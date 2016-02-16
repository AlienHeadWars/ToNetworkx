package model.nx;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;

import model.df.Clazz;
import model.df.Connection;
import model.df.ConnectionType;
import model.df.Feature;
import model.df.Package;
import static java.util.Optional.*;

public class GraphModel {
	private final Map<Feature, Clazz> featuresToClazzes = new HashMap<>();
	private final Map<Clazz, Set<Clazz>> clazzEdges = new HashMap<>();
	private final Map<Package, Set<Package>> packageEdges = new HashMap<>();
	private final Map<String, Clazz> classNames = new HashMap<>();
	private final Map<String, Feature> featureNames = new HashMap<>();
	private final List<Clazz> clazzes;

	public GraphModel(Collection<Package> packages) {
		clazzes = populateClazzes(packages);
		populateNames();
		populateFeaturesToClazzes();
		populateEdges();
		populatePackageEdges(packages);
	}

	private void populatePackageEdges(Collection<Package> packages) {
		final Map<Clazz, Package> classToPackage = new HashMap<>();
		packages.forEach(
				pack -> pack.getClasses().forEach(clazz -> classToPackage.put(clazz, pack)));

		packages.forEach(id -> packageEdges.put(id, new HashSet<>()));

		clazzEdges.keySet().stream().forEach(
				clazz -> clazzEdges.get(clazz).forEach(
						edge -> packageEdges
								.get(classToPackage.get(clazz))
								.add(classToPackage.get(edge))));

		clazzes.stream().forEach(clazz -> {
			Collection<Clazz> edges = clazzEdges.get(clazz);
			getClazzConnections(clazz)
					.forEach((connection) -> edges.add(mapConnection(connection)));
		});
	}

	private void populateNames() {
		clazzes.forEach(clazz -> {
			classNames.put(clazz.getName(), clazz);
			ofNullable(clazz.getFeatures()).ifPresent(
					features -> features
							.forEach(feature -> featureNames.put(feature.getName(), feature)));
		});
	}

	private Collection<Connection> getClazzConnections(Clazz clazz) {
		Collection<Connection> connections = new ArrayList<>();
		ofNullable(clazz.getInBound()).ifPresent(connections::addAll);
		ofNullable(clazz.getOutBound()).ifPresent(connections::addAll);
		ofNullable(clazz.getFeatures()).ifPresent(features -> features.forEach((feature) -> {
			ofNullable(feature.getInbound()).ifPresent(connections::addAll);
			ofNullable(feature.getOutbound()).ifPresent(connections::addAll);
		}));
		return connections;
	}

	private void populateEdges() {
		clazzes.forEach(id -> clazzEdges.put(id, new HashSet<>()));

		clazzes.stream().forEach(clazz -> {
			Collection<Clazz> edges = clazzEdges.get(clazz);
			getClazzConnections(clazz)
					.forEach((connection) -> edges.add(mapConnection(connection)));
		});
	}

	private void populateFeaturesToClazzes() {
		clazzes.stream().forEach(
				clazz -> ofNullable(clazz.getFeatures()).ifPresent(
						features -> features.stream().forEach(
								feature -> featuresToClazzes.put(feature, clazz))));
	}

	private List<Clazz> populateClazzes(Collection<Package> packages) {
		return packages.stream().flatMap(p -> p.getClasses().stream()).collect(toList());
	}

	private Clazz mapConnection(Connection connection) {

		Clazz clazz =
				connection.getType().equals(ConnectionType.CLAZZ)
						? classNames.get(connection.getValue())
						: featuresToClazzes.get(featureNames.get(connection.getValue()));
		return of(clazz).get();
	}

	public Map<Feature, Clazz> getFeaturesToClazzes() {
		return featuresToClazzes;
	}

	public Map<Clazz, Set<Clazz>> getClazzEdges() {
		return clazzEdges;
	}

	public Map<String, Clazz> getClassNames() {
		return classNames;
	}

	public Map<String, Feature> getFeatureNames() {
		return featureNames;
	}

	public List<Clazz> getClazzes() {
		return clazzes;
	}

	public Map<Package, Set<Package>> getPackageEdges() {
		return packageEdges;
	}

}
