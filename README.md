# OBS Toggle

在暂停游戏的同时也暂停 OBS 录制。(1.19.2)

尚未测试。需要搭配 [obs-websocket](https://github.com/obsproject/obs-websocket) 使用。

可能有一些小 bug.

# Usage

直接丢入 `mods` 即可，不需要 fabric-api  
游戏内修改配置需要安装 `ModMenu`。在出现 `Connecting Screen` 之前会卡一小会，这是为了让 OBS 接收到请求之前录制到游戏画面而不是加载页面。  
可以在左上角发现 `Calling OBS` 字样。

# Known Bugs

在游戏菜单里点击返回游戏虽然不会显示加载 Screen 但是仍然会让 OBS 继续录制，所以问题不大。  
使用 Esc 可以叫出那个 Screen.

# And..

不是很会写 Mod, 可能以后再优化了。OBS-Websocket 还没更新到 OBS v29, 我这没法用，所以没测试。