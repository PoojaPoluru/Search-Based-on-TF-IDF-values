                                                                                           //Pooja Mounika Poluru
                                                                                          //ppoluru@uncc.edu


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;                   
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.*;


public class Search extends Configured implements Tool {

	   private static final Logger LOG = Logger .getLogger( Search.class);

	   public static void main( String[] args) throws  Exception {
	      int res  = ToolRunner .run( new Search(), args);
	      System .exit(res);
	   }

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf=new Configuration();//Creating configuration object
		String query="";
		for(int i=2;i<args.length;i++){   //When the query is given after the paths
			query=(query+args[i]+" ");    
			conf.setStrings("Query",query);  //Instantiating conf object and naming it query
			
		}
		// TODO Auto-generated method stub
				Job job3  = Job .getInstance(conf, " Job_3 ");//INstantiating job3 object
        
	      job3.setJarByClass( this .getClass());
	      //Creating Input and Output paths for Job3
	      FileInputFormat.addInputPath(job3,  new Path(args[0]));
	      FileOutputFormat.setOutputPath(job3,  new Path(args[ 1]));
	    //Instantiating the job2 Mapper,Reducer classes
	      job3.setMapperClass( SearchMapper .class);
	      job3.setReducerClass( SearchReducer .class);
	      //Instantiating the Ouput classes of Mapper and Reducer
	      job3.setMapOutputKeyClass(Text.class);
	      job3.setMapOutputValueClass(DoubleWritable.class);
	      job3.setOutputKeyClass( Text .class);
	      job3.setOutputValueClass( DoubleWritable .class);
	      return job3.waitForCompletion( true)  ? 0 : 1;
	
	}

	public static class SearchMapper extends
    Mapper<LongWritable, Text, Text, DoubleWritable>
{
public void map(LongWritable offset, Text lines, Context context)
    throws IOException, InterruptedException{
	context.getInputSplit();
	String line=lines.toString();//Converting Text to String
	String[] splittext=line.split("#####"); //Splitting the LIne by the delimiter
	String[] nextsplit=splittext[1].split("\t");
	Configuration conf=context.getConfiguration();//Instantiating the configuration object
	String x=conf.get("Query");//Getting the Configuration object
	String[] arr=x.split(" ");//Splitting the query by spaces
	
	for(int i=0;i<arr.length;i++){
		if(splittext[0].equals(arr[i])){ //If the word matches the query
			context.write(new Text(nextsplit[0]), new DoubleWritable(Double.parseDouble(nextsplit[1])));//Sending the text file as key and corresponding term frequency as value
		}
	}
	}
	
}

	public static class SearchReducer extends
    Reducer<Text, DoubleWritable, Text, DoubleWritable>
{
public void reduce(Text word, Iterable<DoubleWritable> counts, Context context)
    throws IOException, InterruptedException{
	//int sum=0;
   double sum = 0;
    // Computing Sum of corresponding words and giving it to the output
    for (DoubleWritable count :counts)
    {
        sum += count.get();
    }
	
     context.write(word,  new DoubleWritable(sum));
}
}
}




	