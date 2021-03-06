# Lurker for Spigot 1.11
**Allow players to keep track of their online time, or moderators to keep track of a players online time**
***


### Project
**Status:** *In Development*

**Project Author(s):** WASasquatch (Jordan Thompson)


### Commands

 - /**lurker** *(username|server)*
 

### Permissions

 - lurker.**cmd**
 - lurker.cmd.**others**
 - lurker.cmd.**uptime**
 
 
### Configuration

```yaml
# LURKER CONFIGURATION

# TAGS:
# {USERNAME} - Players display name
# {WEEKS} - Weeks online
# {DAYS} - Days online
# {HOURS} - Hours Online
# {MINUTES} - Minutes Online
# {SECONDS} - Seconds Onlines

# User Offline - use {USERNAME} to display target display name
target-offline-format: "&cThe player &r&l{USERNAME}&c is not online, or doesn't exist!&r"

# Player Time Format - use {USERNAME} to display target display name
player-time-format: "&6&l{USERNAME} &r&2Online Time: &3&l{DAYS}&r&2 days, &3&l{HOURS}&r&2 hours, &3&l{MINUTES}&r&2 minutes, &3&l{SECONDS}&r&2 seconds.&r"

# Self Time Format - No {USERNAME} tag
self-time-format: "&2Online Time: &3&l{DAYS}&r&2 days, &3&l{HOURS}&r&2 hours, &3&l{MINUTES}&r&2 minutes, and &3&l{SECONDS}&r&2 seconds.&r"

# Server Time Format - No {USERNAME} tag
server-time-format: "&2Server has been online for: &6{WEEKS}:{DAYS}:{HOURS}:{MINUTES}:{SECONDS}&r"

# Invalid Argument Format - No special tags
invalid-argument-format: "&cToo many arguments! Cast only on yourself (no username) or on a username.&r"

# No Permission Format - User has no permission
no-permission-format: "&cYou do not have permission to use this command!&r"
```

