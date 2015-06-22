package org.objectprocess.team;

import org.objectprocess.SoapClient.UserValue;

class UserInList extends Object{
  UserValue myUserValue;

  UserInList(UserValue userValue) {
    this.myUserValue = userValue;
  }
  public String toString() {
    return (myUserValue.getFirstName() +" "+ myUserValue.getLastName());
  }
}


