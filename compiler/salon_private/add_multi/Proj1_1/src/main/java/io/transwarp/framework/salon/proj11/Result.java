package io.transwarp.framework.salon.proj11;

public class Result {
  private int value;
  private boolean hasCompileError;

  public Result(int value, boolean hasCompileError) {
    this.value = value;
    this.hasCompileError = hasCompileError;
  }

  public int getValue() {
    return value;
  }

  public boolean hasCompileError() {
    return hasCompileError;
  }

  @Override
  public String toString() {
    return !hasCompileError ?  String.valueOf(value) : "Compile Error";
  }
}
