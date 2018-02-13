/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starfish.core.diagnostics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.w3c.dom.Element;
import starfish.core.common.CommandModule;
import starfish.core.common.Starfish;
import starfish.core.common.Starfish.Log;
import static starfish.core.common.Starfish.getIt;
import static starfish.core.common.Starfish.getMaterialsList;
import starfish.core.io.InputParser;
import starfish.core.materials.KineticMaterial;
import starfish.core.materials.Material;

/**
 *
 * @author Lubos Brieda
 */
public class StatsModule extends CommandModule
{

    /**
     *
     */
    protected int stats_skip = 1;	    //frequency of file saves;
    String file_name = "starfish_stats.csv";
    PrintWriter pw;
	    
    @Override
    public void process(Element element)
    {
	stats_skip = InputParser.getInt("skip", element, stats_skip);
	file_name = InputParser.getValue("file_name",element,file_name);
    }
    
    @Override
    public void start()
    {
	try
	{
	    pw = new PrintWriter(new FileWriter(file_name));
	} catch (IOException ex)
	{
	    Log.error("Failed to open stats file "+file_name);
	}
	
	//for now only saving particle counts
	pw.printf("it,time");	
	for (Material mat:getMaterialsList())
	{
	    if (mat instanceof KineticMaterial)
	    {
		pw.printf(",mp.%s",mat.name);
	    }
	}
	pw.printf(",Ib");
	pw.printf("\n");
    }

    @Override
    public void finish()
    {
	pw.close();
    }
        
    //generates screen and file output

    /**
     *
     */
    public void printStats()
    {
	String msg = String.format("it: %d\t",getIt());
		
	for (Material mat:getMaterialsList())
	{
	    if (mat instanceof KineticMaterial)
	    {
		KineticMaterial km = (KineticMaterial)mat;
		msg += String.format("%s: %d ", km.getName(),km.getNp());
	    }
	}
	
	Starfish.Log.message(msg);
	
	//now save to the file
	if (stats_skip>0 && getIt()%stats_skip == 0)
	    saveStats();
    }
    
    //saves stats to the file

    /**
     *
     */
    protected int lines = 0;

    /**
     *
     */
    protected void saveStats()
    {
	pw.printf("%d,%g",getIt(),Starfish.time_module.getTime());	
	for (Material mat:getMaterialsList())
	{
	    if (mat instanceof KineticMaterial)
	    {
		KineticMaterial km = (KineticMaterial)mat;
		pw.printf(",%g",km.getSpwt0()*km.getNp());
	    }
	}
	pw.printf("\n");
	lines++;
	if (lines>10) {pw.flush();lines=0;}
    }

}
