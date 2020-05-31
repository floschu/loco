# loco - launch on cancel on

<p align=center>
    <a href="https://bintray.com/flosch/loco/loco-core"><img alt="version" src="https://img.shields.io/bintray/v/flosch/loco/loco-core?label=core-version&logoColor=f88909" /></a> 
    <a href="LICENSE"><img alt="license" src="https://img.shields.io/badge/license-Apache%202.0-blue.svg?color=7b6fe2" /></a>
</p>

<p align=center>
    <a href="https://github.com/floschu/loco/"><img alt="last commit" src="https://img.shields.io/github/last-commit/floschu/control?logoColor=ffffff" /></a>
    <a href="https://www.codacy.com/manual/floschu/loco?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=floschu/loco&amp;utm_campaign=Badge_Grade"><img alt="code quality" src="https://api.codacy.com/project/badge/Grade/39072347acb94bf79651d7f16bfa63ca" /></a>
    <a href="https://codecov.io/gh/floschu/loco"><img alt="coverage" src="https://codecov.io/gh/floschu/loco/branch/develop/graph/badge.svg" /></a>
    <a href="https://github.com/floschu/loco/actions"><img alt="build" src="https://github.com/floschu/loco/workflows/build/badge.svg" /></a>
</p>


## installation

``` groovy
repositories {
    jcenter()
}

dependencies {
    implementation("at.florianschuster.loco:loco-core:$version")
}
```

## contains

extensions to **l**aunch **o**n a specific lifecycle event & **c**ancel **o**n a specific lifecycle event

- [lifecycle extensions](loco-core/src/main/kotlin/at/florianschuster/loco/lifecycle.kt)
- [activity extensions](loco-core/src/main/kotlin/at/florianschuster/loco/activity.kt)
- [fragment extensions](loco-core/src/main/kotlin/at/florianschuster/loco/fragment.kt)
- [view extensions](loco-core/src/main/kotlin/at/florianschuster/loco/view.kt)

## author

visit my [website](https://florianschuster.at/).
