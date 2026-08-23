#!/usr/bin/env python3
"""
铜系列贴图生成脚本

从 brass（黄铜）对应贴图调色生成 copper（铜）贴图。
调色策略：HSV 色相旋转（brass ~30° → copper ~15°），饱和度微增，明度保持。

用法：
    python scripts/generate_copper_textures.py

依赖：Pillow (PIL)
"""

import os
import sys
import colorsys
from PIL import Image

# 项目根目录（scripts/ 的上一级）
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
PROJECT_ROOT = os.path.dirname(SCRIPT_DIR)

TEXTURES_DIR = os.path.join(
    PROJECT_ROOT, "src", "main", "resources", "assets", "crystal_clear", "textures", "block"
)
COGWHEELS_DIR = os.path.join(TEXTURES_DIR, "encased_cogwheels")

# brass_gearbox.png 来源：Create 6.0.6 jar 提取
# 该文件不在本仓库内，通过命令行参数或环境变量指定路径
# 环境变量 BRASS_GEARBOX_PATH 优先，其次命令行参数 --brass-gearbox
# 如果都没有提供，尝试在常见位置搜索
def find_brass_gearbox():
    """搜索 brass_gearbox.png 的位置"""
    # 1. 环境变量
    env_path = os.environ.get("BRASS_GEARBOX_PATH")
    if env_path and os.path.exists(env_path):
        return env_path

    # 2. 常见位置搜索
    candidates = [
        # 工作区 .temp/ 目录（向上两级到工作区根目录）
        os.path.join(PROJECT_ROOT, "..", "..", ".temp", "brass_gearbox.png"),
        # Create jar 解压目录（如果设置）
        os.environ.get("CREATE_JAR_EXTRACT", ""),
    ]
    for c in candidates:
        if c and os.path.isabs(c) and os.path.exists(c):
            return c
        if c and os.path.exists(os.path.join(PROJECT_ROOT, c)):
            return os.path.join(PROJECT_ROOT, c)

    return None

# 黄铜 → 铜的调色参数
# brass 主色调 HSV �相约 20-25°，copper 目标色相约 12-15°（参考项目中 copper_casing）
# 高光色（hue > 40°）需要更大幅度旋转，但不能绕到红色区域
HUE_SHIFT = -8.0        # 色相偏移量（度），负值表示向红/橙方向旋转
HUE_SHIFT_HIGHLIGHT = -20.0  # 高光区域的额外色相偏移
HIGHLIGHT_HUE_THRESHOLD = 40.0  # 高于此色相值的像素应用额外偏移
SAT_MULTIPLIER = 1.12   # 饱和度乘数，铜比黄铜略饱和
VAL_ADJUST_DARK = 0.95  # 暗部明度乘数（略微压暗暗部，增强对比）

# 透明像素阈值
ALPHA_THRESHOLD = 10


def shift_hue(r, g, b):
    """
    将单个 RGB 像素从黄铜色调转换为铜色调。
    返回转换后的 (r, g, b) 元组（0-255 整数）。
    """
    # 归一化到 [0, 1]
    rf, gf, bf = r / 255.0, g / 255.0, b / 255.0
    h, s, v = colorsys.rgb_to_hsv(rf, gf, bf)

    # 跳过极暗或极灰的像素（接近黑/灰的结构线条，保持不变）
    if v < 0.08 or s < 0.05:
        return (r, g, b)

    # 色相旋转（以度为单位计算，h 是 0-1 范围）
    hue_deg = h * 360.0
    if hue_deg > HIGHLIGHT_HUE_THRESHOLD:
        # 高光区域：应用更大的色相偏移
        hue_deg += HUE_SHIFT + HUE_SHIFT_HIGHLIGHT
    else:
        # 主色调区域：标准偏移
        hue_deg += HUE_SHIFT

    # 归一化到 [0, 360)
    hue_deg = hue_deg % 360.0
    h = hue_deg / 360.0

    # 饱和度增强
    s = min(1.0, s * SAT_MULTIPLIER)

    # 暗部微压
    if v < 0.45:
        v *= VAL_ADJUST_DARK

    # 转回 RGB
    rf, gf, bf = colorsys.hsv_to_rgb(h, s, v)
    return (int(round(rf * 255)), int(round(gf * 255)), int(round(bf * 255)))


def convert_texture(input_path, output_path):
    """
    读取 brass 贴图，逐像素调色，保存为 copper 贴图。
    保持原始 alpha 通道和图像尺寸不变。
    """
    img = Image.open(input_path)
    original_mode = img.mode

    # 统一转为 RGBA 处理
    rgba = img.convert("RGBA")
    pixels = rgba.load()
    width, height = rgba.size

    for y in range(height):
        for x in range(width):
            r, g, b, a = pixels[x, y]
            if a < ALPHA_THRESHOLD:
                # 透明像素保持不变
                continue
            new_r, new_g, new_b = shift_hue(r, g, b)
            pixels[x, y] = (new_r, new_g, new_b, a)

    # 保存为 RGBA PNG（Minecraft 标准格式）
    rgba.save(output_path, "PNG")

    size = os.path.getsize(output_path)
    print(f"  生成: {os.path.relpath(output_path, PROJECT_ROOT)} "
          f"({width}x{height}, {size} bytes, 原模式={original_mode})")


def main():
    print("=== 铜系列贴图生成 ===\n")

    # 解析命令行参数：--brass-gearbox <path>
    brass_gearbox_path = None
    args = sys.argv[1:]
    i = 0
    while i < len(args):
        if args[i] == "--brass-gearbox" and i + 1 < len(args):
            brass_gearbox_path = args[i + 1]
            i += 2
        else:
            i += 1

    # 如果命令行没指定，尝试自动搜索
    if not brass_gearbox_path or not os.path.exists(brass_gearbox_path):
        brass_gearbox_path = find_brass_gearbox()

    if not brass_gearbox_path:
        print("错误: 找不到 brass_gearbox.png")
        print("请通过以下方式之一指定路径：")
        print("  1. 命令行参数: python scripts/generate_copper_textures.py --brass-gearbox <path>")
        print("  2. 环境变量:   set BRASS_GEARBOX_PATH=<path>")
        sys.exit(1)

    print(f"brass_gearbox.png 路径: {brass_gearbox_path}\n")

    # 定义三组贴图的输入/输出路径
    texture_pairs = [
        {
            "name": "copper_gearbox",
            "input": brass_gearbox_path,
            "output": os.path.join(TEXTURES_DIR, "copper_gearbox.png"),
        },
        {
            "name": "copper_encased_cogwheel_side",
            "input": os.path.join(COGWHEELS_DIR, "brass_encased_cogwheel_side.png"),
            "output": os.path.join(COGWHEELS_DIR, "copper_encased_cogwheel_side.png"),
        },
        {
            "name": "large_copper_encased_cogwheel_side",
            "input": os.path.join(COGWHEELS_DIR, "large_brass_encased_cogwheel_side.png"),
            "output": os.path.join(COGWHEELS_DIR, "large_copper_encased_cogwheel_side.png"),
        },
    ]

    errors = []
    for pair in texture_pairs:
        print(f"处理: {pair['name']}")
        if not os.path.exists(pair["input"]):
            print(f"  错误: 输入文件不存在: {pair['input']}")
            errors.append(pair["name"])
            continue
        # 确保输出目录存在
        os.makedirs(os.path.dirname(pair["output"]), exist_ok=True)
        convert_texture(pair["input"], pair["output"])
        print()

    if errors:
        print(f"完成，但有 {len(errors)} 个错误: {', '.join(errors)}")
        sys.exit(1)
    else:
        print("全部完成，3 个铜系列贴图已生成。")


if __name__ == "__main__":
    main()
