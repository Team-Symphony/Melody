# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## Known Issues

#### General

- Mod configs don't get synced between server and client, which can cause bugs depending on the difference in configurations.
  - Current workaround: Have both client and server both always with the same configurations.

## [Unreleased]

## [0.0.2-alpha+mc1.21.4] 2024-12-24

### Added

#### General

- TRANSLATION: Portuguese from Brazil (pt_br)

## [0.0.1-alpha+mc1.21.3] - 2024-11-8

### Changed

#### General

- Ported to 1.21.3
- Netherite Horse Armor's protection value is now configurable

## [0.0.1-alpha+mc1.21] - 2024-10-23

### Added

#### General

- Added MidnightLib as an embedded dependency.

#### Trans🏳️‍⚧️portation

- FEATURE: Rideable mobs can move through leaves when ridden, at a lower speed ( by default 85% of the original speed)
- FEATURE: Netherite horse armor, which protects 15 armor points, and will completely prevent the horse from bucking if Melody is installed alongside Harmony and both features are enabled

#### Exploration

- FEATURE: Map Book, craftable with an empty map and a book on a cartography table, in which you can add maps by adding the desired map and the mapbook on your inventory's crafting grid or on the cartography table. They can be cloned just like normal maps and stack up to 16.
