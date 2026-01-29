package miun.se.laboration1.weatherapp;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class AsyncWeatherWorker<T> {
    /** Implement these elsewhere (API call + parse). Runs on background thread. */
    public interface BackgroundJob<T> {
        T run() throws Exception;
    }

    /** UI update / error handling. Runs on main thread. */
    public interface UiCallback<T> {
        void onSuccess(T result);
        void onError(Throwable error);
    }

    private final ExecutorService executor;
    private final Handler mainHandler;

    private Future<?> inFlight;

    public AsyncWeatherWorker() {
        this(Executors.newSingleThreadExecutor());
    }

    public AsyncWeatherWorker(ExecutorService executor) {
        this.executor = executor;
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Starts the async task:
     * - background: job.run()
     * - main thread: callback.onSuccess / callback.onError
     */
    public void start(BackgroundJob<T> job, UiCallback<T> callback) {
        cancel(); // ensure only one run at a time

        inFlight = executor.submit(() -> {
            try {
                T result = job.run();
                mainHandler.post(() -> callback.onSuccess(result));
            } catch (Throwable t) {
                mainHandler.post(() -> callback.onError(t));
            }
        });
    }

    /** Cancels the currently running task (best-effort). */
    public void cancel() {
        if (inFlight != null) {
            inFlight.cancel(true);
            inFlight = null;
        }
    }

    /** Call from Activity/Fragment onDestroy to avoid thread leaks. */
    public void shutdown() {
        cancel();
        executor.shutdown();
    }
}


/** Example use
 *
 * AsyncWeatherWorker<MyWeatherModel> worker = new AsyncWeatherWorker<>();
 *
 * worker.start(
 *     () -> apiAndParsingTeam.fetchAndParse(),   // background
 *     new AsyncWeatherWorker.UiCallback<>() {    // main thread
 *         @Override public void onSuccess(MyWeatherModel result) { uiTeam.render(result); }
 *         @Override public void onError(Throwable error) { uiTeam.showError(error); }
 *     }
 * );**/