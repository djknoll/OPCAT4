package opmTypes;

public class opmState {

  String stateName;
  boolean isInitial;
  boolean isFinal;
  boolean isCurrent;

  public opmState(String newStateName, boolean newIsInitial, boolean newIsFinal, boolean newIsDefault) {
    stateName = newStateName;
    isInitial = newIsInitial;
    isFinal = newIsFinal;
    isCurrent = newIsDefault;
  }

  public String getStateName() {
    return stateName;
  }

  public boolean getIsInitial() {
    return isInitial;
  }

  public boolean getIsFinal() {
    return isFinal;
  }

  public boolean getIsCurrent() {
    return isCurrent;
  }

  public void whenEnters() {
    isCurrent = true;
  }

  public void whenExists() {
    isCurrent = false;
  }

}