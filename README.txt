# TchatLink
[![Ask DeepWiki](https://devin.ai/assets/askdeepwiki.png)](https://deepwiki.com/REN-22/tchatlink)

TchatLink is a client-side Minecraft Forge mod that intercepts chat messages and translates them into commands for the Baritone bot. It allows users to control Baritone by simply typing commands into the public game chat.

## Overview

This mod listens for specific patterns in Minecraft's chat. When a player sends a message formatted as a `goto` command, TchatLink automatically converts it into a Baritone-compatible command and executes it. This provides a seamless way to direct the Baritone bot without needing to use the Baritone-specific command prefix (`#`).

The mod is built for Minecraft 1.21.1 using the Forge modding framework.

## Features

*   **Chat Interception**: Monitors incoming player and system chat messages.
*   **Command Parsing**: Recognizes and extracts coordinates from `goto <x> <y> <z>` messages.
*   **Automatic Execution**: Translates recognized messages into Baritone commands (e.g., `#goto <x> <y> <z>`) and executes them.
*   **Client-Side**: The mod operates entirely on the client and does not need to be installed on the server.
*   **Loop Prevention**: Includes filters to prevent the mod from reacting to its own commands or Baritone's output messages.

## Usage

To command Baritone to navigate to specific coordinates, type the following command into the Minecraft chat:

```
goto <x> <y> <z>
```

**Example:**

```
goto 150 75 -300
```

The mod will intercept this message and automatically execute the corresponding command for Baritone: `#goto 150 75 -300`.

## Building from Source

To build the mod from the source code, follow these steps:

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/ren-22/tchatlink.git
    ```

2.  **Navigate to the project directory:**
    ```sh
    cd tchatlink
    ```

3.  **Build the project using the Gradle wrapper:**
    *   On Windows:
        ```bat
        gradlew build
        ```
    *   On macOS/Linux:
        ```sh
        ./gradlew build
        ```

4.  The compiled mod `.jar` file will be available in the `build/libs/` directory. You can then place this file into your Minecraft `mods` folder.
