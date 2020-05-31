package at.florianschuster.loco

import android.app.Activity
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

internal val IndefiniteTestFlow = flow {
    while (true) {
        delay(100)
        emit(Unit)
    }
}

internal enum class TestType { CreateDestroy, StartStop, ResumePause }

internal const val TestTypeKey: String = "TestType"

internal inline fun <reified A : Activity> launchActivityWith(testType: TestType): ActivityScenario<A> {
    val intent = Intent(ApplicationProvider.getApplicationContext(), A::class.java).apply {
        putExtra(TestTypeKey, testType.name)
    }
    return launchActivity(intent)
}

internal fun Activity.requireTestTypeInIntent(): TestType {
    return enumValueOf(requireNotNull(intent.extras?.getString(TestTypeKey)))
}

internal inline fun <reified F : Fragment> launchFragmentWith(testType: TestType): FragmentScenario<F> {
    return launchFragmentInContainer<F>(fragmentArgs = bundleOf(TestTypeKey to testType.name))
}

internal fun Fragment.requireTestTypeInBundle(): TestType {
    return enumValueOf(requireNotNull(requireArguments().getString(TestTypeKey)))
}