[![License](https://img.shields.io/github/license/Blackoutburst/Holo-API.svg)](LICENSE)
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
    .setLocation(loc);
HoloManager.spawnHolo(holo, event.getPlayer());
```
