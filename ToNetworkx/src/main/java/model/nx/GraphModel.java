package model.nx;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;

import model.df.Clazz;
import model.df.Connection;
import model.df.ConnectionType;
import model.df.Package;
import static java.util.Optional.*;

public class GraphModel {
	private final Map<String, String> featuresToClazzes = new HashMap<>();
	private final Map<String, Integer> clazzesToId = new HashMap<>();
	private final Map<Integer, Collection<Integer>> classesToEdges = new HashMap<>();
	private final List<Clazz> clazzes;

	private final AtomicInteger clazzIds;

	public GraphModel(Collection<Package> packages, Boolean onlyConfirmed) {
		clazzIds = new AtomicInteger(1);
		clazzes = populateClazzes(packages);
		populateClazzIds();

		populateFeaturesToClazzes();
		populateEdges();
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
		clazzesToId
				.values()
				.parallelStream()
				.forEach(id -> classesToEdges.put(id, new HashSet<>()));

		clazzes.parallelStream().forEach(clazz -> {
			Collection<Integer> edges = classesToEdges.get(clazzesToId.get(clazz.getName()));
			getClazzConnections(clazz)
					.forEach((connection) -> edges.add(mapConnection(connection)));
		});
	}

	private void populateFeaturesToClazzes() {
		clazzes.parallelStream().forEach(
				clazz -> ofNullable(clazz.getFeatures()).ifPresent(
						features -> features.parallelStream().forEach(
								feature -> featuresToClazzes
										.put(feature.getName(), clazz.getName()))));
	}

	private void populateClazzIds() {
		clazzes.parallelStream().forEach(
				clazz -> clazzesToId.put(clazz.getName(), clazzIds.getAndIncrement()));
	}

	private List<Clazz> populateClazzes(Collection<Package> packages) {
		return packages
				.parallelStream()
				.flatMap(p -> p.getClasses().parallelStream())
				.collect(toList());
	}

	private Integer mapConnection(Connection connection) {
		return clazzesToId.get(
				connection.getType().equals(ConnectionType.CLAZZ)
						? connection.getValue()
						: featuresToClazzes.get(connection.getValue()));
	}

	public Map<String, String> getFeaturesToClazzes() {
		return featuresToClazzes;
	}

	public Map<String, Integer> getClazzesToId() {
		return clazzesToId;
	}

	public Map<Integer, Collection<Integer>> getClassesToEdges() {
		return classesToEdges;
	}

	@JsonIgnore
	public List<Clazz> getClazzes() {
		return clazzes;
	}

	public AtomicInteger getClazzIds() {
		return clazzIds;
	}

}
