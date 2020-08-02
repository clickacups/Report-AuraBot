# Report-AuraBot

This plugin includes a Report system and a working Aura Bot.
The Report System allows players to submit reports on other players, which are saved in the config.

# Report Help

<b>Commands</b>:

> <b>/report [name] [reason]</b> - Submits a report for a player, saving it in the config and notifying all players with the permission report.recievenotis<br>
  <b>Permission</b>: report.send
  
> <b>/reports [name]</b> - Gets the reports submitted for that player, returns a timestamp, reason and the player who originally sent the report<br>
  <b>Permission</b>: report.getreports
  
> <b>/clearreports [name]</b> - Clears the reports for the player specified<br> 
  <b>Permission</b>: report.clearreports
  
<b>Permissions</b>:
  >  <b>report.*</b>:
    - Gives access to all report commands, should only be given to administators<br>
 children:<br>
      report.send: true<br>
      report.clearreports: true<br>
      report.recievenotis: true<br>
      report.getreports: true<br>
      
  > <b>report.send</b>:
      - Allows the player to report
      - <b>default</b>: true
      
  > <b>report.clearreports</b>:
      - Allows the player to clear the reports for a user
      - <b>default</b>: op
      
  > <b>report.getreports</b>:
      - Allows the player to recieve the reports of a player.
      - <b>default</b>: op
      
  > <b>report.recievenotis</b>:
      - Gives the player a notification when a player is reported
      - <b>default</b>: op
      
When the plugin is first ran, the config will automatically set an AuraBot Report value (default 3), this is how many reports a player can get before they summon an AuraBot.

# AuraBot Help

An AuraBot is a generated NPC that moves around the player, testing if the player is using killaura (or tp aura).
The AuraBot has no name and default skin.

<b>Commands</b>:

> <b>/aurabot <name></b> - Summons an AuraBot that is assigned to the player, the AuraBot will teleport around the player for 5 seconds. If a player hits the AuraBot too many times then a message will be sent to all players with the permission aurabot.recievenotis (on default the max amount of hits is 35, this can be changed in the config)<br>
  <b>Permission</b>: aurabot.summon
  
> <b>/aurabot threshold <int></b> - Sets the maximum amount of hits a player can get on the AuraBot before all players with the permission aurabot.recievenotis are notified.<br>
  <b>Permission</b>: aurabot.managethreshold
  
<b>Permissions</b>:

  > <b>aurabot.*</b> -
    Gives access to all aurabot commands, should only be given to administators<br>
    children:<br>
      aurabot.summon: true<br>
      aurabot.recievenotis: true<br>
      aurabot.managethreshold: true<br>

  > <b>aurabot.summon</b> -
      Allows the player to summon an aurabot to check on another player -
      <b>default</b>: op
      
  > <b>aurabot.recievenotis</b> -
      Allows the player to recieve notifications on players that have broken the aurabot threshold -
  <b>default</b>: op
  
  > <b>aurabot.managethreshold</b> -
      Allows the player to change the threshold of the aurabot via /aurabot threshold <int> -
  <b>default</b>: op
  

  

      


