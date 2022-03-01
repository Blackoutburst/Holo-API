[![License](https://img.shields.io/github/license/Blackoutburst/Holo-API.svg)](LICENSE)
[![Release](https://img.shields.io/github/release/Blackoutburst/Holo-API.svg)](https://github.com/Blackoutburst/Holo-API/releases)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/25425037816047b480a91a3e2b4119b7)](https://www.codacy.com/gh/Blackoutburst/Holo-API/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Blackoutburst/Holo-API&amp;utm_campaign=Badge_Grade)

# Holo-API
Simple API to create Minecraft Holograms using spigot/bukkit 
Paper/Spigot version 1.8.8 (v1_8_R3)

In your plugin.yml
```yml
depend: [HoloAPI]
```

Spawn Holo

```java
Holo holo = new Holo(UUID.randomUUID(), "§6Test")
    .setLocation(player, new Location(world, 0, 5, 0, 0, 0))
    .addLine("§bThis is a new line")
    .addLine("§aLine 3");
HoloManager.spawnHolo(player, holo);
```

![image](https://user-images.githubusercontent.com/30992311/156257108-b9799fc9-5466-40d2-a78d-d6e26eded128.png)
