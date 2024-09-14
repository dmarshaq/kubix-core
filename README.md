# kubix-core

Based on Java and [LWJGL](https://www.lwjgl.org/), this library provides core kubix engine functionality to create games.

Table Of Contents
-----------------

* [Quick Start](#quick-start)
    * [Requirements](#requirements)
    * [Build](#build)
    * [How to use](#how-to-use)
    * [Kubix Hello World](#kubix-hello-world)
* [Documentation](#documentation)
* [About](#about)
* [License](#license)
* [Collaboration](#collaboration)

Quick Start
----------------
#### Requirements
* Java (JDK 21+)
* Apache Maven
* Git

#### Build
In order to build the project using Maven:

  + **Maven CLI** (Command Line Interface). In this case you need to install Maven. See <a href="https://maven.apache.org/install.html" target="_blank" title="Maven install">here</a> for more detail.
  Here is the instruction:
    1. **Open** terminal or your prompt and **navigate to** the directory where your projects are stored.
    2. **Clone** Git repository:
        ```
        git clone https://github.com/dmarshaq/kubix-core.git
        ```
    3. **Execute** the following command in the cloned Git project:
        ```
        mvn clean install
        ```
#### How to use
***
- To use this library, add following dependency into your own project's pom file:
  ```xml
  <dependencies>
      <dependency>
          <groupId>org.dmarshaq.kubix</groupId>
          <artifactId>kubix-core</artifactId>
          <version>1.0-SNAPSHOT</version>
      </dependency>
  </dependencies>
  ```

#### Kubix Hello World
***
- To start using the Kubix functionality, create 3 following classes that inherit from kubix-core classes:

  ``` java
  public class GameContext extends Context {
      public GameContext(Supplier<List<String>> resourcesSupplier) {
          super(resourcesSupplier);
      }

      @Override
      public void initContextProperties() {
          setTitle("My Game");
          setFullScreen(false);
          setMinScreenUnitWidth(16);
          setMinScreenUnitHeight(9);
          setClearColor(new Color(60, 100, 100, 255));
      }
  }
  ```
  ```java
  public class GameGraphic extends Graphic {
      @Override
      protected void initShaders() {
          // Initialization of your shaders.
      }
  
      @Override
      protected void modifyShaders() {
          // Modification of your shaders.
      }
  }
  ``` 
  ```java
  public class GameUpdate extends Update {
      private Camera camera;

      @Override
      public void start() {
          // Initializing Camera.
          camera = new Camera(0f, 0f, Context.getMinScreenUnitWidth(), Context.getMinScreenUnitHeight());
      }

      @Override
      public void update () {
          // Draws green square in the center of the screen.
          getSnapshot().addQuad(GraphicCore.quad(
                  new Vector3(-1f, -1f, 0f),
                  2f,
                  2f,
                  new Vector4(0f, 1f, 0f, 1f),
                  Texture.NO_TEXTURE_REGION,
                  Context.SHADERS.get(Context.SHADER_BASIC_QUAD),
                  Context.LAYERS.get("default")
          ));
          // Loads camera into snapshot, that will be rendered after this update() call has been finished.
          getSnapshot().setCamera(new CameraDto(camera.projectionMatrix()));
      }
  }
  ```
***
- In order to initialize the Kubix engine, add the following to your project's main file:
  ```java
  public static void main(String[] args) {
          new GameContext(() -> Arrays.stream(new String[] {}).toList());
          new GameUpdate();
          new GameGraphic();
          KubixEngine.initialize();
      }
  }
  ```


[Documentation](https://docs.google.com/document/d/1_wIsn72VMj0yZyPul4AXNrJckJ18v4UT8ziGXgCWfrc/edit?usp=sharing)
----------------

About
----------------
This project is a minimalistic game engine created to experiment with Java and LWJGL. It handles efficient rendering through batch processing, integrates basic vector and matrix math utilities, and includes a customizable game loop. A key goal is to provide surface-level tools that simplify game development, making it easier to prototype interactive games and applications while gaining hands-on experience with the essential building blocks of a game engine.

License
----------------
The MIT License (MIT)

Copyright (c) 2024 Daniil Yarmalovich

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

Collaboration
----------------
Please enter an issue in the repositorty for any questions or problems.
Alternatively, please contact us at mrshovdaniil@gmail.com
