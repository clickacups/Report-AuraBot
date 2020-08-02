# Report-AuraBot

This plugin includes a Report system and a working Aura Bot.
The Report System allows players to submit reports on other players, which are saved in the config.

# Report Help

Commands:

> /report <name> <reason> - Submits a report for a player, saving it in the config and notifying all players with the permission report.recievenotis
  Permission: report.send
  Default: True
  
> /reports <name> - Gets the reports submitted for that player, returns a timestamp, reason and the player who originally sent the report.
  Permission: report.getreports
  Default: Op
  
> /clearreports <name> - Clears the reports for the player specified
  Permission: report.clearreports
  Default: Op
  
> Permissions:
  >  report.*:
    description: Gives access to all report commands, should only be given to administators
    children:
      report.send: true
      report.clearreports: true
      report.recievenotis: true
      report.getreports: true
  > report.send:
      description: Allows the player to report
      default: true
  > report.clearreports:
      description: Allows the player to clear the reports for a user
      default: op
  > report.getreports:
      description: Allows the player to recieve the reports of a player.
      default: 
  > report.recievenotis:
      description: Gives the player a notification when a player is reported
      default: op
      
When the plugin is first ran, the config will automatically set an AuraBot Report value (default 3), this is how many reports a player can get before they summon an AuraBot.

# AuraBot Help

An AuraBot is a generated NPC that moves around the player, testing if the player is using killaura (or tp aura).
The AuraBot has no name and default skin.

Commands:

> /aurabot <name> - Summons an AuraBot that is assigned to the player, the AuraBot will teleport around the player for 5 seconds. If a player hits the AuraBot too many times then a message will be sent to all players with the permission aurabot.recievenotis (on default the max amount of hits is 35, this can be changed in the config)
  Permission: aurabot.summon
  
> /aurabot threshold <int> - Sets the maximum amount of hits a player can get on the AuraBot before all players with the permission aurabot.recievenotis are notified.
  Permission: aurabot.managethreshold
  
Permissions:

  > aurabot.*:
    description: Gives access to all aurabot commands, should only be given to administators
    children:
      aurabot.summon: true
      aurabot.recievenotis: true
      aurabot.managethreshold: true

  > aurabot.summon:
      description: Allows the player to summon an aurabot to check on another player
      default: op
  > aurabot.recievenotis:
      description: Allows the player to recieve notifications on players that have broken the aurabot threshold
      default: op
  > aurabot.managethreshold:
      description: Allows the player to change the threshold of the aurabot via /aurabot threshold <int>
      default: op
  

  

      


