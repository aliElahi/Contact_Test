package com.saat.contacttest.workerTest;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.testing.TestWorkerBuilder;

import com.saat.contacttest.model.worker.ReadAllContactWorker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RunWith(AndroidJUnit4.class)
public class ReadAllWorkerTest {

    private Context context;
    private Executor executor;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        executor = Executors.newSingleThreadExecutor();
    }

    @Test
    public void testReadAllContactWorker() {
        ReadAllContactWorker worker = TestWorkerBuilder.from(context,
                        ReadAllContactWorker.class,
                        executor)
                        .build();

        Worker.Result result = worker.doWork();

        assert result.equals(ListenableWorker.Result.success());

    }
}
