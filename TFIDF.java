                                                                                          //Pooja Mounika Poluru
                                                                                          //ppoluru@uncc.edu

import java.io.File;
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
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;


public class TFIDF extends Configured implements Tool {

   private static final Logger LOG = Logger .getLogger( TFIDF.class);

   public static void main( String[] args) throws  Exception {
	   ToolRunner .run( new TermFrequency(), args);//Calling the CTErmFRequency class using ToolRunner 
      int res  = ToolRunner .run( new TFIDF(), args);
      System .exit(res);
   }

   public int run( String[] args) throws  Exception {
       
     Configuration conf=new Configuration();//Creating the configuration object
     int l=new File(args[0]).listFiles().length;//Finding out the length of files in the path 
     conf.setInt("length", l);   //setting configuration parameters of l to "length"
      Job job2  = Job .getInstance(conf, " Job_2 ");  //Creating Job object
      job2.setJarByClass( this .getClass());
      FileInputFormat.addInputPaths(job2,  args[1]);  //Creating path for the Job2 input
      FileOutputFormat.setOutputPath(job2,  new Path(args[ 2]));  //Creating path for Job2 output
      //Instantiating the job2 Mapper,Reducer and combiner class
      job2.setMapperClass( Mapper2 .class);
      job2.setReducerClass( Reducer2 .class);
      job2.setCombinerClass(x.class);
      //Instantiating the Ouput classes of Mapper and Reducer 
      job2.setMapOutputKeyClass(Text.class);
      job2.setMapOutputValueClass(Text.class);
      job2.setOutputKeyClass( Text .class);
      job2.setOutputValueClass( DoubleWritable .class);


      return job2.waitForCompletion( true)  ? 0 : 1;
   }
 
 
 
   public static class Mapper2 extends
    Mapper<LongWritable, Text, Text, Text>
{
public void map(LongWritable offset, Text lineText, Context context)
    throws IOException, InterruptedException
{
    context.getInputSplit();
    String line=lineText.toString();//Converting the Text to String
    String concat;
    String[] splittext=line.split("####");
    String[] nextsplit=splittext[1].split("\t");
    concat=nextsplit[0]+"="+nextsplit[1];//Combining the FileName with the corresponding Frequency values
    Configuration conf=context.getConfiguration();//Creating the Configuration Object
    conf.setInt("length", conf.getInt("length",0));//Getting the Configuration parameter
    Text key=new Text(splittext[0]); //Assigning the word to the key
    Text value=new Text(concat);
     
    context.write(key, value);
}
}

 
   public static class Reducer2 extends Reducer<Text ,  Text ,  Text ,  DoubleWritable > {
         
          public void reduce( Text word,  Iterable<Text> file,  Context context)
             throws IOException,  InterruptedException {
            for(Text list:file){
                String temp[]=list.toString().split(",");//Split the files based on the ","
                for(int i=0;i<temp.length;i++){
                
                    String value[]=temp[i].split("=");//Splitting files using the delimiter = for different filename and frequency 
                    Configuration conf=context.getConfiguration();//Getting the configuration parameters
                    int l=conf.getInt("length",0);//Getting the number of the files
                    double total=1+(l/temp.length);
                    double idf = Math.log10(total);//Calculating the IDF 
                    double tdidf=Double.parseDouble(value[1])*idf;//Calculating the TFIDF
                    context.write(new Text(word+"#####"+value[0]),new DoubleWritable(tdidf));//Appending the delimiter with the file name to the word 
                    
                }
                //System.out.println(temp[0]);
            }
              
          }
         
       }
   public static class x extends Reducer<Text, Text, Text, Text>//Combiner class
    {
	   //Combiner is used to append the key values to the filenames and their frequencies
	   //
       public void reduce(Text word, Iterable<Text> value,
                Context context) throws IOException, InterruptedException
            {String sum="";
           for (Text count : value) {
               if(sum.equals("")){
                   sum=count.toString();
               }
               else
                sum =sum+","+ count.toString();
            }//System.out.println(sum);
           context.write(word, new Text(sum));
            }
        
 }
}