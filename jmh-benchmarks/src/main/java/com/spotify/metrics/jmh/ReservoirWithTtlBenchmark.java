package com.spotify.metrics.jmh;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Snapshot;
import com.spotify.metrics.core.HistogramWithTtl;
import com.spotify.metrics.core.ReservoirWithTtl;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ReservoirWithTtlBenchmark {

  private Histogram histogram;

  @Setup
  public void setUp() {
    histogram = new Histogram(new ReservoirWithTtl());
  }

  @Benchmark
  @Group("updateAndSnapshot")
  @GroupThreads(10)
  public void update() {
    histogram.update(42);
  }

  @Benchmark
  @Group("updateAndSnapshot")
  @GroupThreads(1)
  public Snapshot getSnapshot() {
    return histogram.getSnapshot();
  }
}
