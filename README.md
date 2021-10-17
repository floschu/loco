<p align="center">
    <b>loco - launch on 🚀 cancel on 💥</b></br></b></br>
</p>

# Deprecated
You can now use the native [Lifecycle.repeatOnLifecycle](https://developer.android.com/reference/kotlin/androidx/lifecycle/package-summary?hl=id#repeatonlifecycle)

-------
-------
-------
-------

<p align=center>
    <a href="https://bintray.com/flosch/loco/loco-core"><img alt="version" src="https://img.shields.io/bintray/v/flosch/loco/loco-core?label=core-version&logoColor=f88909" /></a> 
    <a href="LICENSE"><img alt="license" src="https://img.shields.io/badge/license-Apache%202.0-blue.svg?color=7b6fe2" /></a>
</p>

<p align=center>
    <a href="https://github.com/floschu/loco/"><img alt="last commit" src="https://img.shields.io/github/last-commit/floschu/control?logoColor=ffffff" /></a>
    <a href="https://www.codacy.com/manual/floschu/loco?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=floschu/loco&amp;utm_campaign=Badge_Grade"><img alt="code quality" src="https://api.codacy.com/project/badge/Grade/39072347acb94bf79651d7f16bfa63ca" /></a>
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

### [lifecycle extensions](loco-core/src/main/kotlin/at/florianschuster/loco/lifecycle.kt)

example:

```kotlin
val lifecycle: Lifecycle
lifecycle.launchOnCreateCancelOnDestroy {
    /**
     * these will be launched once the lifecycle is created and cancelled once the 
     * lifecycle is destroyed. it is also relaunched on each create.
     */
    someFlow().launchIn(this)
    launch { someSuspendingFunction() }
}
```

### [activity extensions](loco-core/src/main/kotlin/at/florianschuster/loco/activity.kt)

example:

```kotlin
class MainActivity : Activity(R.layout.activity_main) {
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 
        launchOnLifecycleCreateCancelOnLifecycleDestroy {
            /**
             * this will be launched once the Activity is created and cancelled once the 
             * activity is destroyed. it is also relaunched on each create.
             */
            button.clicks().onEach {
                // do something
            }.launchIn(this)
        }
    }
}
```

### [fragment extensions](loco-core/src/main/kotlin/at/florianschuster/loco/fragment.kt)

example:

```kotlin
class SomeFragment : Frgment(R.layout.fragment_some) {
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 
        launchOnViewLifecycleStartCancelOnViewLifecycleStop {
            /**
             * this will be launched once the fragment view lifecycle is started and cancelled  
             * once the fragment view lifecycle is stopped. it is also relaunched on each start.
             */
            button.clicks().onEach {
                // do something
            }.launchIn(this)
        }
    }
}
```

### [view extensions](loco-core/src/main/kotlin/at/florianschuster/loco/view.kt)

example view attach/detach:

```kotlin
class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
 
    init {
        launchOnViewAttachCancelOnViewDetach {
            /**
             * this will be launched once the view is attached to a window and cancelled
             * once the view is detached from the window. it is also relaunched in each
             * attach to window.
             */
            someFlow().launchIn(this)
            launch { suspendingFunction() }
        }
    }
}
```

example view-tree-lifecycle:

```kotlin
class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
 
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launchOnLifecycleResumeCancelOnLifecyclePause {
            /**
             * this will be launched once the view tree lifecycle is resumed and cancelled  
             * once the view tree lifecycle is paused. it is also relaunched on each resume.
             */
            someFlow().launchIn(this)
            launch { suspendingFunction() }
        }
    }
}
```

## author

visit my [website](https://florianschuster.at/).
