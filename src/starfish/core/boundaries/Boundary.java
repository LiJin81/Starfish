/* *****************************************************
 * (c) 2012 Particle In Cell Consulting LLC
 * 
 * This document is subject to the license specified in 
 * Starfish.java and the LICENSE file
 * *****************************************************/
package starfish.core.boundaries;

import java.util.ArrayList;
import starfish.core.common.Constants;
import starfish.core.common.Starfish;
import starfish.core.domain.Mesh.NodeType;
import starfish.core.materials.Material;
import starfish.core.source.ParticleListSource;
import starfish.core.source.Source;

/*Boundary
 * 
 * Spline with additional information, such as boundary behavior 
 * and particle sources
 */

/**
 *
 * @author Lubos Brieda
 */

public class Boundary extends Spline
{

    /**
     *
     * @param name
     * @param type
     * @param value
     * @param mat
     */
    public Boundary (String name, NodeType type, double value, Material mat)
    {
	this.name = name;
	this.type = type;
	this.value = value;	
	this.material = mat;
    }
	
    /**allocates sources*/
    protected void init()
    {
	/*set boundaries for segments*/
	for (int i=0;i<numSegments();i++)
	    this.getSegment(i).setParentInfo(this,i);
	   
	/*allocate memory for particle list sources, 
	* need to allocate for each material since we are indexing by material index*/
	particle_list_source = new ParticleListSource[Starfish.getMaterialsList().size()];
	   
	/*allocate only for flying mats*/
	for (Material mat:Starfish.getMaterialsList())
	{
	    ParticleListSource source = new ParticleListSource(mat, this);
	    addSource(source);
	    particle_list_source[mat.getIndex()] = source;
	}
    }

    /*sets boundary temperature*/
    void setTemp(double temp) 
    {
	this.temp = temp;    	
    }

    /**return thermal velocit
     * @param material
     * @return y*/
    public double getVth(Material material) {
	return Math.sqrt(2*Constants.K*temp/material.getMass());
    }
   
    /*accessors*/

    /**
     *
     * @return
     */

    public String getName() {return name;}

    /**
     *
     * @return
     */
    public NodeType getType() {return type;}

    /**
     *
     * @return
     */
    public double getValue() {return value;}
 
    /** returns default materia
     * @return l*/
    public Material getMaterial() {return material;}
	
    /** returns material at spline position
     * @param tt
     * @return */
    public Material getMaterial (double t) {return material;}	/*TODO: for now this assumes uniform mat*/
    
    /** returns material inde
     * @return x*/
    public int getMaterialIndex() {return mat_index;}

    /** source list*/
    protected ArrayList<Source> source_list = new ArrayList<Source>();  

    /**
     *
     * @return
     */
    public ArrayList<Source> getSourceList() {return source_list;}

    /**
     *
     * @param source
     */
    public void addSource(Source source){source_list.add(source);}
 
    /** list of particle list sources*/
    protected ParticleListSource particle_list_source[];

    /**
     *
     * @param mat_index
     * @return
     */
    public ParticleListSource getParticleListSource(int mat_index) {return particle_list_source[mat_index];}
			
    /*variables*/

    /**
     *
     */

    protected final NodeType type;

    /**
     *
     */
    protected double value;

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected Material material;

    /**
     *
     */
    protected int mat_index;

    /**
     *
     */
    protected double temp;		/*temparture in Kelvin*/

    /**
     *
     */
    protected double v_th;		/*thermal velocity, used for thermal accomodation*/
}
