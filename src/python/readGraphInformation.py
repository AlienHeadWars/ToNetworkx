import networkx as nx
import math
import time
import json
import os
from statistics import mean
def getPercentile(percentile, list):
		return list[math.ceil(len(list)*(percentile/100))-1];
def numberGenerator( max ):
	i=0
	while i<max:
		i=i+1
		yield i
def getCoefficientsByPercentile(degreesList, richClubCoefficients):
	Coefficients=[]
	for percentile in numberGenerator(100):
		try:
			Coefficients.append(richClubCoefficients[getPercentile(percentile,degreesList)])
		except KeyError:
			Coefficients.append(richClubCoefficients[len(richClubCoefficients)-1])
	return Coefficients
def getStats(filepath):
	print ("compiling stats for " +filepath)
	Graph=nx.read_adjlist(filepath)
	DegreesList=[]
	Degrees=Graph.degree()
	for Degree in Degrees:
		DegreesList.append(Degrees[Degree]);
	GraphSize=len(DegreesList)
	DegreesList.sort()
	def richClubCoefficientsFunction():
		return nx.rich_club_coefficient(Graph,normalized=True);
	def richClubCoefficientsNoNormalisationFunction():
		return nx.rich_club_coefficient(Graph,normalized=False);
	def averageShortestPathsFunction():
		return nx.average_shortest_path_length(Graph);
	def average_clusteringFunction():
		return nx.average_clustering(Graph);
	def degree_assortativity_coefficientFunction():
		return nx.degree_assortativity_coefficient(Graph);
	Stats={}
	def timeStats( label, function):
		Stats[label]={}
		Stats[label]['times']=[]
		try:
			for iteration in numberGenerator(1):
				print ("calculating " + label+ " for " +filepath +", iteration: "+ str(iteration))
				StartTime=time.clock()
				Result=function()
				EndTime=time.clock()
				Stats[label]['Result']=Result
				Stats[label]['times'].append(EndTime-StartTime)
			Stats[label]['averageTime']=mean(Stats[label]['times'])
		except:
			Stats[label]['Result']="uncomputable"
			Stats[label]['averageTime']="uncomputable"
	timeStats('RichClubCoefficients', richClubCoefficientsFunction);
	timeStats('RichClubCoefficientsNoNormalisation', richClubCoefficientsNoNormalisationFunction);
	timeStats('Assortativity', degree_assortativity_coefficientFunction);
	timeStats('AverageShortestPath', averageShortestPathsFunction);
	timeStats('AverageClustering', average_clusteringFunction);
	RichClubCoefficients=Stats['RichClubCoefficients']['Result']
	EightyFirstPercentileDegree=getPercentile(81,DegreesList)
	EightyFirstPercentileDegreeRichClubCoefficient=RichClubCoefficients[EightyFirstPercentileDegree]
	Stats['EightyFirstPercentileDegree']=EightyFirstPercentileDegree
	Stats['EightyFirstPercentileDegreeRichClubCoefficient']=EightyFirstPercentileDegreeRichClubCoefficient
	Stats['EightyFirstPercentileDegree']=EightyFirstPercentileDegree
	Stats['CoefficientsByPercentile']=getCoefficientsByPercentile(DegreesList,RichClubCoefficients)
	Stats['CoefficientsByPercentileNoNormalisation']=getCoefficientsByPercentile(DegreesList,Stats['RichClubCoefficientsNoNormalisation']['Result'])
	Stats['GraphSize']=GraphSize
	return Stats;
def output(path, obj):
	with open(path,'w') as outfile:
		json.dump(obj, outfile);
		
def processStats( inputPath, outputFolderForMethods, outPutFolderForStatsByGraph ):
	dicOfStats={}
	for file in os.listdir(inputPath):
		Stat=getStats(inputPath+"/"+file)
		dicOfStats[file]=Stat
		output(outPutFolderForStatsByGraph+'/'+file,Stat)
	FirstStatGroup=next (iter (dicOfStats.values()))
	for statKey in FirstStatGroup:
			if (type(FirstStatGroup[statKey]) is dict) :
				def getResult(stats):
					return stats[statKey]['Result']
				def getAverageTime(stats):
					return stats[statKey]['averageTime']
				output(outputFolderForMethods+'/'+statKey+"_value",getStatSummary(dicOfStats,getResult))
				output(outputFolderForMethods+'/'+statKey+"_averageTime",getStatSummary(dicOfStats,getAverageTime))
			else:
				def getStat(stats):
					return stats[statKey]
				output(outputFolderForMethods+'/'+statKey,getStatSummary(dicOfStats,getStat));


def getStatSummary( dicOfStats, methodToGetStats ):
	StatsByGraph={}
	for stats in dicOfStats:
		StatsByGraph[stats]=methodToGetStats(dicOfStats[stats])
	return StatsByGraph;



