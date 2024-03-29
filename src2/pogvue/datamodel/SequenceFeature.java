package pogvue.datamodel;

import pogvue.util.Format;
import pogvue.io.GFFFile;
import pogvue.datamodel.motif.*;

import java.awt.*;

import java.util.*;

public class SequenceFeature {
    public static int DISPLAY_ID = 0;
    public static int DISPLAY_REGION = 1;
    public static int DISPLAY_HITID = 2;
    public static int DISPLAY_GFF = 3;

    public int display_type = 3;

    public int      start;
    public int      end;
    public String   type;
    public String   description;
    public Color    color;
    public Sequence sequence;
    public String   id;
    public double   score;
    public double   score2;
    public int      hitlen;
    public int      strand;
    public double   pvalue;
    public double   pid;
    public String   type2;
    public SequenceFeature hit = null;
    public String   phase = ".";

    public String alignString;
    public Hashtable scoreVector = null;

    public Sequence seq;
    public TFMatrix tfm;

    public  Hashtable scores = null;

    Vector  feat;

    public static final int CHAIN    = 0;
    
    public static final int DOMAIN   = 1;


    public static final int TRANSMEM = 2;
    
    public static final int SIGNAL   = 3;
    public static final int HELIX    = 4;
    public static final int TURN     = 5;
    public static final int SHEET    = 6;
    public static final int CARBOHYD = 7;
    public static final int ACT_SITE = 8;
    
    public static final int TRANSIT  = 9;
    public static final int VARIANT  = 10;
    public static final int BINDING  = 11;

    public static void main(String[] args) {

      SequenceFeature sf = new SequenceFeature();
		  
      System.out.println("Feature " + sf);
    }

    public SequenceFeature() {
    }

    public SequenceFeature(Sequence sequence,String type, int start, int end, String description) {
	this.sequence = sequence;
	this.type = type;
	this.start = start;
	this.end = end;
	this.description = description;
	
	setColor();
    }
    public void setTFMatrix(TFMatrix tfm) {
	this.tfm = tfm;
    }
    public TFMatrix getTFMatrix() {
	return tfm;
    }
    public void addFeature(SequenceFeature sf) {
	if (feat == null) {
	    feat = new Vector();

	    feat.addElement(sf);
	    
	    start  = sf.getStart();
	    end    = sf.getEnd();
	    
	    strand = sf.getStrand();
	    type   = sf.getType();
	} else {
	    feat.addElement(sf);

	    if (sf.getStart() < start) {
		start = sf.getStart();
	    }
	    if (sf.getEnd() > end) {
		end   = sf.getEnd();
	    }
	}
    }
    public void addFeatures(Vector feat) {
	for (int i = 0; i < feat.size(); i++) {
	    SequenceFeature sf = (SequenceFeature)feat.elementAt(i);

	    addFeature(sf);
	}
    }
    public SequenceFeature clone() {
	SequenceFeature newf = new SequenceFeature(sequence,type,start,end,description);
	newf.setId(id);
	newf.setStrand(strand);
	newf.setScore(score);
	newf.setScores(scores);
                 
	if (getHitFeature() != null) {
	    newf.setHitFeature(getHitFeature().clone());
	}
	return newf;
    }
    public void draw(Graphics g, int fstart, int fend, int x1, int y1, int width, int height) {
	g.setColor(new Color((float)(Math.random()),(float)(Math.random()),(float)(Math.random())));

	int xstart = start;
	int xend = end;
	long tstart = System.currentTimeMillis();

	if (!(xend < fstart && xstart > fend)) {
	    
	    if (xstart > fstart) {
		x1 = x1 + (xstart-fstart)*width;
		fstart = xstart;
	    }
	    
	    if (xend < fend) {
		fend = xend;
	    }
	    
	    for (int i = fstart; i <= fend; i++) {
		String s = sequence.sequence.substring(i,i+1);
		if (!(s.equals(".") || s.equals("-") || s.equals(" "))) {
		    g.fillRect(x1+(i-fstart)*width,y1,width,height);
		} else {
		    g.drawString("-",x1+(i-fstart)*width,y1+height);
		}
	    }
	    
	}
	long tend = System.currentTimeMillis();
	System.out.println("Time = " + (tend-tstart) + "ms");
	
    }
    public String getAlignString() {
	return alignString;
    }
    public Color getColor() {
	return color;
    }
    public String getDescription() {
	return description;
    }
    public int getEnd() {
	return end;
    }
    public Vector getFeatures() {
	return feat;
    }
    public SequenceFeature getHitFeature() {
	return hit;
    }
    public int getHitlen() {
	return hitlen;
    }
    public String getId() {
	return this.id;
    }
    public int getLength() {
	return (end - start + 1);
    }
    double getPercentId() {
	return pid;
    }
    public String getPhase() {
	return phase;
    }
    
    double getPValue() {
	return pvalue;
    }
    
    public double getScore() {
	return score;
    }
    
    public double getScore2() {
	return score2;
    }
    
    public double getScoreAt(int i) {
	if (scores != null && scores.containsKey(i)) {
	    return ((Double)scores.get((int)i)).doubleValue();
	} else {
	    return 0.0;

	}
    }
    public Hashtable getScores() {
	return scores;
    }
    public Hashtable getScoreVector() {
	return scoreVector;
    }
    public Vector getScoreVectorAt(int i) {
	if (scoreVector != null && scoreVector.containsKey(i)) {
	    return (Vector)scoreVector.get((int)i);
	} else {
	    return new Vector();
	}
    }
    public Sequence getSequence() {
	return sequence;
    }
    
    public int getStart() {
	return start;
    }
    public int getStrand() {
	return strand;
    }
    public String getType() {
	return type;
    }
    public String getType2() {
	return type2;
    }

    public String print() {
	String tmp = new Format("%15s").form(type);
	tmp = tmp +  new Format("%6d").form(start);
	tmp = tmp +  new Format("%6d").form(end);
	tmp = tmp +  " " + description;
	return tmp;
    }
    public void setAlignString(String str) {
	this.alignString = str;
    }
    private void setColor() {
	if (type.equals("CHAIN")) {
	    color = Color.white;
	} else if (type.equals("DOMAIN")) {
	    color = Color.white;
	} else if (type.equals("TRANSMEM")) {
	    color = Color.red.darker();
	} else if (type.equals("SIGNAL")) {
	    color = Color.cyan;
	} else if (type.equals("HELIX")) {
	    color = Color.magenta;
	} else if (type.equals("TURN")) {
	    color = Color.cyan;
	} else if (type.equals("SHEET")) {
	    color = Color.yellow;
	} else if (type.equals("STRAND")) {
	    color = Color.yellow;
	} else if (type.equals("CARBOHYD")) {
	    color = Color.pink;
	} else if (type.equals("ACT_SITE")) {
	    color = Color.red;
	} else if (type.equals("TRANSIT")) {
	    color = Color.orange;
	} else if (type.equals("VARIANT")) {
	    color = Color.orange.darker();
	} else if (type.equals("BINDING")) {
	    color = Color.blue;
	} else if (type.equals("DISULFID")) {
	    color = Color.yellow.darker();
	} else if (type.equals("NP_BIND")) {
	    color = Color.red;
	} else if (type.indexOf("BIND") > 0) {
	    color = Color.red;
	} else {
	    color = Color.lightGray;
	}
    }

    public void setEnd(int end) {
	this.end = end;
    }

    public void setHitFeature(SequenceFeature hit) {
	this.hit = hit;
    }
    public void setHitlen(int len) {
	this.hitlen = len;
    }

    public void setId(String id) {
	this.id = id;
    }

    void setPercentId(double pid) {
	this.pid = pid;
    }
  
  public void setPhase(String phase) {
    this.phase = phase;
  }
  void setPValue(double value) {
    this.pvalue = value;
  }
  public void setScore(double score) {
    this.score = score;
  }
  public void setScore2(double s) {
    this.score2 = s;
  }
  public void setScores(Hashtable hash) {
    this.scores = hash;
  }
  public void setScoreVector(Hashtable hash) {
    this.scoreVector = hash;
  }
  public void setSequence(Sequence seq) {
    this.sequence = seq;
  }
  public void setStart(int start) {
    this.start = start;
  }
  public void setStrand(int strand) {
    this.strand = strand;
  }
  public void setType(String type) {
this.type = type;
	}
  public void setType2(String type2) {
this.type2 = type2;
	}
    public String toGFFString() {
	String gff = id + "\t" + type + "\tfeature\t" + start + "\t" + end + "\t" + score + "\t" + strand + "\t" + phase;
	
	if (hit != null) {
	    gff = gff + "\t" + hit.getId() + "\t" + hit.getStart() + "\t" + hit.getEnd() + "\t" + hit.getStrand();
	}
	return gff;
    }
    public String toString() {
	if (display_type == 0) {
	    return getId();
	} else if (display_type == 1) {
	    return getId() + "." + getStart() + "-" + getEnd();
	} else if (display_type == 2) {
	    if (getHitFeature() != null) {
		return getHitFeature().getId();
	    }
	}
	return toGFFString();
    }
    public static Vector hashFeatures(Vector infeat, int offset,
				      LinkedHashMap typeorder, boolean bump) {

	Hashtable hash = new Hashtable();
	Vector    feat = new Vector();

	// Hash the features into features by type

	for (int i = 0; i < infeat.size(); i++) {
	    SequenceFeature sf = (SequenceFeature) infeat.elementAt(i);

	    sf.setStart(sf.getStart() - offset);
	    sf.setEnd  (sf.getEnd()   - offset);
	    
	    String type = sf.getType();

	    if (hash.containsKey(type)) {
	      GFF tmp = (GFF) hash.get(type);
		tmp.addFeature(sf);
	    } else {
		Vector tmp = new Vector();
		tmp.addElement(sf);
		GFF tmpg = new GFF(type, "", 1, 2);
		tmpg.addFeature(sf);
		hash.put(type, tmpg);
	    }
	}

	// Start the output vector with known types
	if (typeorder != null) {
	  Iterator pogit = typeorder.keySet().iterator();

	  while (pogit.hasNext()) {
	    String next = (String) pogit.next();

	    if (hash.containsKey(next)) {
	      GFF tmp = (GFF) hash.get(next);
	      if (bump) {
		Vector tmpvec = GFFFile.bumpGFF(tmp);
		for (int i = 0; i < tmpvec.size(); i++) {
		  feat.addElement(tmpvec.elementAt(i));
		}
	      } else {
		feat.addElement((GFF) hash.get(next));
	      }
	    }
	  }
	}

	// Add the remaining features
	Enumeration pogen2 = hash.keys();

	while (pogen2.hasMoreElements()) {
	    String type = (String) pogen2.nextElement();
	    if (typeorder == null || typeorder.keySet().contains(type) == false) {

		GFF tmp = (GFF) hash.get(type);

		if (bump) {
		    Vector tmpvec = GFFFile.bumpGFF(tmp);
		    for (int i = 0; i < tmpvec.size(); i++) {
			feat.addElement(tmpvec.elementAt(i));
		    }
		} else {
		    feat.addElement((GFF) hash.get(type));
		}
	    }
	}

	return feat;
    }

    public ChrRegion getRegion() {
	return new ChrRegion(id,start,end,strand);
    }
    public boolean overlaps(ChrRegion reg) {
	if (reg.getChr().equals(id) && (!(reg.getStart() > end || reg.getEnd() < start))) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean searchId(String str) {
	if (getId().indexOf(str) >= 0  ||
	    getPhase().indexOf(str) >=0) {
	    return true;
	}
    
	if (getHitFeature() != null &&
	    getHitFeature().getId().indexOf(str) >= 0) {
	    return true;
	}
	return false;
    }
}

