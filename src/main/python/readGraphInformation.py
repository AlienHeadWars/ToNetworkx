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
			for iteration in numberGenerator(2):
				print ("calculating " + label+ " for " +filepath +", iteration: "+ str(iteration))
				StartTime=time.clock()
				Result=function()
				EndTime=time.clock()
				Stats[label]['Result']=Result
				Stats[label]['times'].append(EndTime-StartTime)
			Stats[label]['averageTime']=mean(Stats[label]['times'])
		except:
			Stats[label]="uncomputable"
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
def processFiles( inputPath, outputPath, processFileMethod):
	for file in os.listdir(inputPath):
		output(outputPath+'/'+file,processFileMethod(inputPath+"/"+file));
def processStats( inputPath, outputPath ):
		processFiles( inputPath, outputPath, getStats);
processStats("c:/graphdata/graphs","c:/graphdata/graphstats")