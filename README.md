# Scala Monkey Engine (wrapper for jMonkeyEngine)

## Installation


Start sbt:

```
$ sbt
```

Run one example:

```
sbt> runHelloPhysics
```

### Build details

This is a normal sbt project, you can compile code with `sbt compile` and run it
with `sbt run`, `sbt console` will start a Dotty REPL. For more information on
cross-compilation in sbt, see http://www.scala-sbt.org/0.13/docs/Cross-Build.html

For more information on the sbt-dotty plugin, see the
[dotty-example-project](https://github.com/lampepfl/dotty-example-project/blob/master/README.md).

### Build caveats

1. Currently there [is a bug](https://github.com/bbarker/SME/issues/1) in build.sbt's depedency management that affects physics games using the [Bullet](http://bulletphysics.org/) library. If you compile and run [this program](https://github.com/bbarker/HelloCollision-on-Gradle) on your system, it should serve as a workaround for now, in that you can then run your physics/Bullet programs with SBT.

## References

* Cloned from [here](https://github.com/bbarker/SME_dotty_update).