# Uranium

Uranium is a moderation utility built for Eaglercraft 1.8 servers. **THIS PLUGIN WILL NOT WORK ON CRAFTBUKKIT 1.5.2, SO DO NOT TRY IT!**

# How to Install
- Download the latest jarfile from the Releases page
- Drag and drop into your **1.8.8 server**'s plugin folder
- Restart, **not reload**, your server
- Modify the configuration file located in `plugins/Uranium/config.yml` to your liking
- ????
- Profit

# Bungee Configuration

Certain features of Uranium require modifications to the Bungee config.

### IP Banning

The first modification you will need to make is allowing Bukkit to send console command packets to Bungee via plugin messaging.

To do this, go to your Bungee config, and set `accept_bukkit_console_command_packets` to `true`.

### IP/Domain

For commands like `/ip` and `/domain` to work, you will need to disable the Bungee versions of those commands.

To do this, just add `ip` and `domain` to `disabled_commands` in the Bungee config, like this:
```yaml
disabled_commands:
- ip
- domain
```

**Note**: These commands will still work in console, they will just not work in-game.

# Permissions

| Permission                | Description                                                                                               |
|---------------------------|-----------------------------------------------------------------------------------------------------------|
| uranium.notify            | Receive all staff-only alerts, such as suspicious domains.                                                |
| uranium.bypass            | Bypass the chat filter.                                                                                   |
| uranium.reload            | Reload the plugin configuration file.                                                                     |
| uranium.banip             | Will allow any user with this permission to IP ban users online.                                          |
| uranium.banip.exempt      | Allows the user to be made exempt from the above command.                                                 |
| uranium.ip                | Gives the user the ability to view any user's IP. Half of the IP will be censored for user privacy.       |
| uranium.ip.full           | Instead of half the IP being censored, having this permission will allow the user to view the whole IP.   |
| uranium.domain            | Gives the user the ability to view any user's domain.                                                     |
| uranium.vanish            | Allows the user to vanish, making them invisible to everyone except other users who have this permission. |
| uranium.commandspy        | Gives the user access to spy on other user commands.                                                      |
| uranium.commandspy.exempt | Makes the user exempt from their commands showing up on command spy.                                      |