package fr.limsi.negotiate;

public abstract class Proposal {

   protected Status status = Status.OPEN;
   protected Proposal counter;
   public final boolean isSelf;
   
   protected Proposal (boolean isSelf) {
      this.isSelf = isSelf;
   }
   
   public void setStatus (Status status) {
      this.status = status;
   }
   
   public Status getStatus () { return status; }
   
   public void setCounter (Proposal counter) {
      this.counter = counter;
   }
   
   public Proposal getCounter () { return counter; }
   
   public static enum Status { OPEN, REJECTED, ACCEPTED }
   
}
