package main.java.com.codepath.recursion.towerofhanoi;

public class Disc 
{
	public int curr;
	public int prev;
	public int dest;
	public int weight;
	
	public Disc(int weight, int curr, int pre, int moveTo)
	{
		this.weight = weight;
		this.curr = curr;
		this.prev = pre;
		this.dest = moveTo; 
	}

	/**
	 * Sets dest.
	 *
	 * @param d the d
	 */
	public void setDest(int d)
	{
		this.dest = d;
	}

	/**
	 * Gets weight.
	 *
	 * @return the weight
	 */
	public String getWeight() {
		Integer W = new Integer(weight);
		return W.toString();
	}

	/**
	 * Gets dest.
	 *
	 * @return the dest
	 */
	public String getDest() {
		Integer D = new Integer(dest);
		return D.toString();
	}
	
	// Update the this instances current position, previous position, and destination
	public void upDate(int pp)
	{		
		curr = dest;
		prev = pp;
		dest = 3-(curr+prev);
	}
}

