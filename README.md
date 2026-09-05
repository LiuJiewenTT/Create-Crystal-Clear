# Create: Crystal Clear

给机械动力（Create）的传动杆、齿轮和脚手架添加透明玻璃机壳包覆。

## 简介

本模组为 Create 的轴（Shaft）、小齿轮（Cogwheel）、大齿轮（Large Cogwheel）和脚手架（Scaffolding）提供三种透明机壳：

- **玻璃机壳（Glass Casing）** — 半透明外壳，可以看见内部机械结构
- **透明玻璃机壳（Clear Glass Casing）** — 更通透的玻璃，视野更好
- **照明机壳（Illumination Casing）** — 自发光外壳，兼具装饰与照明

每种机壳支持 4 种材质：安山岩（Andesite）、黄铜（Brass）、铜（Copper）、火车（Train）。

## 支持的方块

| 包覆类型 | 可包覆目标 | 材质 |
|----------|-----------|------|
| 玻璃机壳 / 透明玻璃机壳 | 装饰用机壳方块 | andesite / brass / copper / train |
| 照明机壳 | 装饰用机壳方块 | andesite / brass / copper / train |
| 玻璃包覆轴 / 透明玻璃包覆轴 / 照明包覆轴 | Create 传动杆 | andesite / brass / copper / train |
| 玻璃包覆小齿轮 / 透明玻璃包覆小齿轮 / 照明包覆小齿轮 | Create 小齿轮 | andesite / brass / copper / train |
| 玻璃包覆大齿轮 / 透明玻璃包覆大齿轮 / 照明包覆大齿轮 | Create 大齿轮 | andesite / brass / copper / train |
| 玻璃包覆水管 / 透明玻璃包覆水管 / 照明包覆水管 | Create 铜水管 | andesite / brass / copper / train |
| 玻璃脚手架 / 透明玻璃脚手架 | Create 金属脚手架 | andesite / brass / copper |

> 注：Create 原版的普通铜机壳已可包覆水管，本模组在此基础上增加了玻璃/透明玻璃/照明机壳的包覆变体。

## 用法

1. 制作对应材质的玻璃机壳或照明机壳
2. 潜行右键点击已放置的传动杆 / 齿轮 / 水管 / 脚手架，即可包覆
3. 创造模式标签页默认只显示机壳方块；可在配置中开启"显示包覆变体"以在创造栏中直接获取包覆后的方块

## 技术信息

- **Minecraft**：1.21.1
- **NeoForge**：21.1.x
- **Create**：6.0.x（最低 6.0.4）
- **Java**：21
- **许可证**：MIT

## 致谢

- [Simibubi / Create Team](https://github.com/Creators-of-Create/Create) — Create 模组作者
- [Cyvack](https://github.com/Cyvack) — 原始 Create Crystal Clear mod 作者
- [cinnamondev](https://github.com/cinnamondev) — Create 6.0+ 修复版参考
- [Oleh Boichuk (c0nnor263)](https://github.com/c0nnor263) — Create 6.0+ 修复版参考
- [Adonis (adonis-baffin)](https://github.com/adonis-baffin/CreatePrism) — CreatePrism 重构项目作者
- [Lonely Star (LoneStarFateZero)](https://github.com/LoneStarFateZero/CreatePrism) — CreatePrism 修复版作者

## 从源码构建

```bash
git clone https://github.com/LiuJiewenTT/Create-Crystal-Clear.git
cd Create-Crystal-Clear
./gradlew build
```

构建产物位于 `build/libs/`。