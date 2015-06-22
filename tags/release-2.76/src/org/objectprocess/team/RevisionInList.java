package org.objectprocess.team;

import org.objectprocess.SoapClient.MetaRevisionValue;

class RevisionInList extends Object{
  MetaRevisionValue myMetaRevisionValue;

  RevisionInList(MetaRevisionValue metaRevisionValue) {
    this.myMetaRevisionValue = metaRevisionValue;
  }
  public String toString() {
    return ("      " + myMetaRevisionValue.getMajorRevision() + "."
                     + myMetaRevisionValue.getMinorRevision());
  }
}


