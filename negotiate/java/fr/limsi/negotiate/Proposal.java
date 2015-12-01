package fr.limsi.negotiate;
/**
 * 
 * define  propositions made  during dialogue. For each proposition, we define its status for the begining open, counter represent the
 * counter proposition that the other interlocutor may do and finally isSelf is true if the proposition is made by the agent
 *
 */
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
