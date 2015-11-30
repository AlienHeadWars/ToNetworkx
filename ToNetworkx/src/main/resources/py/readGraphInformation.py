import networkx as nx
import math
import datetime
import json
import os
def getPercentile(percentile, list):
		return list[math.ceil(len(list)*(percentile/100))-1];
def numberGenerator( max ):
	i=0
	while i<max:
		i=i+1
		yield i
def getCoefficientsByPercentile(degreesList, richClubCoefficients):
	CoefficientMap={}
	for percentile in numberGenerator(100):
		try:
			CoefficientMap[percentile]=richClubCoefficients[getPercentile(percentile,degreesList)]
		except KeyError:
			CoefficientMap[percentile]=richClubCoefficients[len(richClubCoefficients)-1]
	return CoefficientMap
def getStats(filepath):
        print ("compiling stats for " +filepath)
	Graph=nx.read_adjlist(filepath)
	DegreesList=[]
	Degrees=Graph.degree()
	for Degree in Degrees:
		DegreesList.append(Degrees[Degree]);
	GraphSize=len(DegreesList)
	DegreesList.sort()
	RichClubCoefficients=nx.rich_club_coefficient(Graph,normalized=True)
	EightyFirstPercentileDegree=getPercentile(81,DegreesList)
	EightyFirstPercentileDegreeRichClubCoefficient=RichClubCoefficients[EightyFirstPercentileDegree]
	Stats={}
        def timeStats( label, function):
                Stats[label]={}
                Stats[label]["times"]={}
                try:
                for iteration in numberGenerator(100):                
                        StartTime=datetime.datetime.now()
                        Result=function
                        EndTime=datetime.datetime.now()
                        Stats[label]["Result"]=function
                        Stats[label]["times"][iteration]=StartTime-EndTime
                except:
                        Stats[label]="uncomputable"
                        Stats[label[iteration]]=-1
        def
	Stats['RichClubCoefficients']=RichClubCoefficients
	Stats['EightyFirstPercentileDegree']=EightyFirstPercentileDegree
	Stats['EightyFirstPercentileDegreeRichClubCoefficient']=EightyFirstPercentileDegreeRichClubCoefficient
	Stats['EightyFirstPercentileDegree']=EightyFirstPercentileDegree
	Stats['Assortativity']= nx.degree_assortativity_coefficient(Graph)
        Stats['AverageShortestPath']= nx.average_shortest_path_length(Graph)
	Stats['AverageClustering']= nx.average_clustering(Graph)
	Stats['CoefficientsByPercentile']=getCoefficientsByPercentile(DegreesList,RichClubCoefficients)
	Stats['GraphSize']=GraphSize
	return Stats;
def output(path, obj):
	with open(path,'w') as outfile:
		json.dump(obj, outfile)
def processFiles( inputPath, outputPath, processFileMethod):
	for file in os.listdir(inputPath):
		output(outputPath+'/'+file,processFileMethod(file))
def processStats( inputPath, outputPath ):
        processFiles( inputPath, outPutPath, getStats)




	
