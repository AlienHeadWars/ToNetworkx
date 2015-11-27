>>> import networkx as nx
>>> filePath="C:/graphs/jackson-2.0"
>>> Graph=nx.read_adjlist(filePath)
>>> DegreesList=[]
>>> Degrees=Graph.degree()
>>> for Degree in Degrees:
	DegreesList.append(Degrees[Degree]);
>>> GraphSize=len(DegreesList)
>>> DegreesList.sort()
>>> EightyFirstPercentileDegree=DegreesList[int(GraphSize*.81)]
>>> RichClubCoefficients=nx.rich_club_coefficient(Graph,normalized=True)
>>> import json
>>> def output(objeact, filepath):
	Outfile=open(filepath, w);
	json.dump(data, outfile)
	Outfile.close;
                  
