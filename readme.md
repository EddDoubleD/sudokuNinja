# Sudoku Ninja
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)<br/>
Application solves sudoku using [Cache-Karp method](https://www.nature.com/articles/srep00725)

## Build docker
``` bash
# build docker image 
docker build --build-arg JAR_FILE=target/sudokuNinja-0.0.1-SNAPSHOT.jar -t edddoubled/sudoku_ninja:1.0.0 .
# run docker image
docker run -d --name sudoku-ninja -p 8080:8080 edddoubled/sudoku_ninja:1.0.0
```
## Helpfulness
- [The Chaos Within Sudoku](https://www.nature.com/articles/srep00725)
- [A Variable Order Runge-Kutta Method for Initial Value Problems with Rapidly Varying Right-Hand Sides](http://www.elegio.it/mc2/rk/doc/p201-cash-karp.pdf)
- [Optimization hardness as transient chaos in an analog approach to constraint satisfaction](https://arxiv.org/pdf/1208.0526.pdf)
- [Docker Image Size Reduction Techniques](https://habr.com/ru/company/ruvds/blog/485650/)
