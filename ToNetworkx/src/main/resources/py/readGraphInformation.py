>>> import networkx as nx
>>> filePath="C:/graphs/jackson-2.0"
>>> Graph=nx.read_adjlist(filePath)
>>> DegreesList=[]
>>> Degrees=Graph.degree()
>>> for Degree in Degrees:
	DegreesList.append(Degrees[Degree]);
>>> GraphSize=len(DegreesList)
>>> DegreesList.sort()
>>> RichClubCoefficients=nx.rich_club_coefficient(Graph,normalized=True)
>>> def getPercentile(percentile, list):
	return list[math.ceil(len(list)*(percentile/100))-1];
>>> EightyFirstPercentileDegree=getPercentile(81,DegreesList)
>>> EightyFirstPercentileCoefficient=RichClubCoefficients[EightyFirstPercentileDegree]
