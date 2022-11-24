# Eriantys Board Game

[![License: MIT][license-image]][license]

<img src="https://github.com/christian-confalonieri/Eriantys-Prova-Finale-Ingegneria-del-Software-2021-2022/blob/main/src/main/resources/assets/Eriantys_scatola3D%2Bombra.png" width=192px height=192 px align="right" />

Eriantys Board Game is the final test of **"Software Engineering"**, course of **"Computer Science Engineering"** held at Politecnico di Milano (2021/2022).

**Professor**: Alessandro Margara

**Final Score**: 30 cum laude / 30 

## Project specification
The project consists of a Java version of the board game *Eriantys*, made by Cranio Creations.

You can find the full game [here](https://www.craniocreations.it/prodotto/eriantys/).

The final version includes:
* initial UML diagram;
* final UML diagram, generated from the code by automated tools;
* working game implementation, which has to be rules compliant;
* source code of the implementation;
* source code of unity tests.

## Features
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules | 🟢 |
| Hard Mode | 🟢 |
| Socket | 🟢 |
| GUI | 🟢 |
| CLI | 🟢 |
| Multiple games | 🟢 |
| All Characters cards | 🟢 |
| Team Games | 🟢 |
| Persistance | 🔴 |

#### Legend
🔴 Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;🟡 Implementing&nbsp;&nbsp;&nbsp;&nbsp;🟢 Implemented

## Test cases
All tests cover **100%** of model's classes and **81%** of model methods.

**Coverage criteria: code lines.**

| Package | Coverage |
|:-----------------------|:------------------------------------:|
| **Model (All)** | **696/867 (81%)** |
| Entity | 221/241 (91%) |
| Enumeration | 27/34 (79%) |
| Game | 245/345 (71%) |
| Power | 203/247 (82%) |

## Usage

### Server
`java -jar EryantisServer.jar [#PORTNUMBER]`

### Client
`java -jar EryantisClient.jar [-p|--port #PORTNUMBER] [-a|--address #ADDRESS] [-cli|-gui|-debug]`

## Instructions

| **[CLI][cli-instructions-link]**     | **[GUI][gui-instructions-link]**     | **[JAVADOC][javadoc-instructions-link]**
|-------------------------------------|-------------------------------------|-------------------------------------|
|[<img width="120px" src="https://github.com/christian-confalonieri/Eriantys-Prova-Finale-Ingegneria-del-Software-2021-2022/blob/main/src/main/resources/assets/wizards/blueWizard.jpg" />][cli-instructions-link] |[<img width="120px" src="https://github.com/christian-confalonieri/Eriantys-Prova-Finale-Ingegneria-del-Software-2021-2022/blob/main/src/main/resources/assets/wizards/greenWizard.jpg" />][gui-instructions-link] |[<img width="120px" src="https://github.com/christian-confalonieri/Eriantys-Prova-Finale-Ingegneria-del-Software-2021-2022/blob/main/src/main/resources/assets/wizards/purpleWizard.jpg" />][javadoc-instructions-link] 

## Team
|   Name                  |   Email                               |
|-------------------------|---------------------------------------|
| Leonardo Airoldi        | leonardo.airoldi@mail.polimi.it       |
| Christian Confalonieri  | christian.confalonieri@mail.polimi.it |
| Alessandro Ettore       | alessandro.ettore@mail.polimi.it      |

## Copyright and license

This project is copyright 2022.

Licensed under the **[MIT License][license]**; you may not use this software except in compliance with the License.

[license]: https://github.com/christian-confalonieri/Eriantys-Prova-Finale-Ingegneria-del-Software-2021-2022/blob/main/LICENSE
[license-image]: https://img.shields.io/badge/License-MIT-blue.svg
[cli-instructions-link]: https://github.com/christian-confalonieri/Eriantys-Prova-Finale-Ingegneria-del-Software-2021-2022/blob/main/deliveries/jar/cli-instructions.txt
[gui-instructions-link]: https://github.com/christian-confalonieri/Eriantys-Prova-Finale-Ingegneria-del-Software-2021-2022/blob/main/deliveries/jar/gui-instructions.txt
[javadoc-instructions-link]: https://github.com/christian-confalonieri/Eriantys-Prova-Finale-Ingegneria-del-Software-2021-2022/tree/main/deliveries/JavaDoc
