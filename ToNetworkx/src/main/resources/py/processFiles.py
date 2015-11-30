>>> import json
>>> import os
>>> def output(path, obj):
	with open(path,'w') as outfile:
		json.dump(obj, outfile)
>>> def processFiles( inputPath, outputPath, processFileMethod):
	for file in os.listdir(inputPath):
		output(outputPath+'/'+file,processFileMethod(file))
