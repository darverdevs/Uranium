# Uranium

Uranium is a moderation utility built for Eaglercraft 1.8 servers. **THIS PLUGIN WILL NOT WORK ON CRAFTBUKKIT 1.5.2, SO DO NOT TRY IT!**

## How to Install
- Download the latest jarfile from the Releases page
- Drag and drop into your **1.8.8 server**'s plugin folder
- Restart, **not reload**, your server
- Modify the configuration file located in `plugins/Uranium/config.yml` to your liking
- ????
- Profit

## IP Banning

Since Eaglercraft uses a different protocol, getting IP addresses via Bukkit's normal methods is not possible. For this reason, Uranium utilizes plugin messaging.

Additionally, IP banning is done at the Bungee level, which means that you need to modify your EaglerBungee config for this portion of the plugin to work as expected.

You will need to modify `accept_bukkit_console_command_packets` in your Bungee configuration, and set it to `true`. This is to allow IP banning from in-game.