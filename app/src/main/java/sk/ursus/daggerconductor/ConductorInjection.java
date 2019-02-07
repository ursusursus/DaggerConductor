package sk.ursus.daggerconductor;

import android.app.Activity;
import com.bluelinelabs.conductor.Controller;
import dagger.android.DispatchingAndroidInjector;
import dagger.internal.Preconditions;

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 21.12.2018.
 */
public class ConductorInjection {

    // Usage same as dagger.android
    //    @Component(
    //            modules = [
    //              dagger.android.AndroidInjectionModule::class,
    //              ContributesModule::class,
    //              ...
    //            ]
    //    )

    //    @Module
    //    abstract class ContributesModule {
    //        @ContributesAndroidInjector abstract fun mainController(): MainController
    //        @ContributesAndroidInjector abstract fun fooController(): ChannelsController
    //         ...
    //    }

    public static void inject(Controller controller) {
        Preconditions.checkNotNull(controller, "controller");
        HasControllerInjector hasDispatchingControllerInjector = findHasControllerInjector(controller);
        DispatchingAndroidInjector<Controller> controllerInjector = hasDispatchingControllerInjector.controllerInjector();
        Preconditions.checkNotNull(controllerInjector, "%s.controllerInjector() returned null",
                hasDispatchingControllerInjector.getClass().getCanonicalName());
        controllerInjector.inject(controller);
    }

    private static HasControllerInjector findHasControllerInjector(Controller controller) {
        Controller parentController = controller;

        do {
            if ((parentController = parentController.getParentController()) == null) {
                Activity activity = controller.getActivity();
                if (activity == null) {
                    throw new IllegalArgumentException("activity is null");
                }

                if (activity instanceof HasControllerInjector) {
                    return (HasControllerInjector) activity;
                }

                if (activity.getApplication() instanceof HasControllerInjector) {
                    return (HasControllerInjector) activity.getApplication();
                }

                throw new IllegalArgumentException(
                        String.format("No injector was found for %s", new Object[]{controller.getClass().getCanonicalName()}));
            }
        } while (!(parentController instanceof HasControllerInjector));

        return (HasControllerInjector) parentController;
    }
}
