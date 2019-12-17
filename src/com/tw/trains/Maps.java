package com.tw.trains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tw.trains.data.TownsEnum;
import com.tw.trains.exception.TrainsException;

/**
 * 路线算法api实现
 * @author elf
 *
 */
public class Maps {

	public static final String NO_SUCH_ROUTE = "NO SUCH ROUTE";
	public static final String ROUTE_JOINER = "-";
	
	public static Map<Town, Map<String, Route>> directTownsMaps;
	public static Map<Town, Map<String, Route>> routeMaps;
	
	public static void init()
	{
		//Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
		if(null == Maps.directTownsMaps)
		{
			Maps.directTownsMaps = new HashMap<Town, Map<String, Route>>();
		}
		if(null == Maps.routeMaps)
		{
			Maps.routeMaps = new HashMap<Town, Map<String, Route>>();
		}
		TownsEnum[] initData = TownsEnum.values();
		for(TownsEnum te : initData)
		{
			if (Maps.directTownsMaps.containsKey(te.getStart())) {
				Maps.directTownsMaps.get(te.getStart()).put(
						te.getEnd().getName(),
						new Route(te.getStart(), te.getEnd(), te.getRoutes(),
								te.getDistance()));
			} else {
				Map<String, Route> diretTowns = new HashMap<String, Route>();
				diretTowns.put(te.getEnd().getName(), new Route(te.getStart(),
						te.getEnd(), te.getRoutes(), te.getDistance()));
				Maps.directTownsMaps.put(te.getStart(), diretTowns);
			}
		}
		
		//System.out.println("Maps.directTownsMaps init success! " +  Maps.directTownsMaps);
	}

	/**
	 * 根据给定路线计算距离
	 * @param route 路线
	 * @return
	 * @throws TrainsException
	 */
	public static int getDistance(Route route) throws TrainsException
	{
		if(null == route)
		{
			return 0;
		}
		
		Town startTown = route.getStartTown();
		
		// 如果已缓存路线，可以直接取出结果
		if (null != Maps.routeMaps && Maps.routeMaps.containsKey(startTown)
				&& Maps.routeMaps.get(startTown).containsKey(route.getRoutes())) {
			return Maps.routeMaps.get(startTown).get(route.getRoutes())
					.getDistance();
		}
		
		int distance = 0;
		if(null != Maps.directTownsMaps.get(startTown))
		{
			
			String[] allTowns = route.getRoutes().split("-");
			
			if (allTowns.length == 2) {
				if (null == Maps.directTownsMaps.get(startTown).get(
						route.getEndTown().getName())) {
					throw new TrainsException(Maps.NO_SUCH_ROUTE);
				}

				distance += Maps.directTownsMaps.get(startTown)
						.get(route.getEndTown().getName()).getDistance();
			}
            else {
				if (null == Maps.directTownsMaps.get(startTown).get(allTowns[1])) {
					throw new TrainsException(Maps.NO_SUCH_ROUTE);
				} else {
					Route tmp = new Route(new Town(allTowns[1], allTowns[1]),
							new Town(allTowns[2], allTowns[2]), route
									.getRoutes().substring(2));
					distance += Maps.directTownsMaps.get(startTown).get(allTowns[1])
							.getDistance()
							+ Maps.getDistance(tmp);
				}
			}
			
			// 将算好的路线缓存起来
			cacheRoute(route, startTown, distance);
			
			return distance;
		}
		
		throw new TrainsException(Maps.NO_SUCH_ROUTE);
	}
	
	
	/**
	 * 根据给定起始站，以及允许最大站数，计算有多少种路线
	 * @param route 带有起始站信息的路线
	 * @param countTowns 跨站数
	 * @param max true：指定数最大跨站数 false：指定数为确定站数
	 * @return
	 */
	public static int getRoutesCount(Route route, int countTowns, boolean max)
	{
		if(null == route || 0 == countTowns)
		{
			return 0;
		}
		
		Map<String, Route> routes = Maps.getRoutes(route, countTowns, max, false, 0);
		if(max)
		{
			return null == routes ? 0 : routes.size();
		}
		else
		{
			if(null != routes && !routes.isEmpty())
			{
				//System.out.println(routes);
				Map<String, Route> result = new HashMap<String, Route>();
				Set<String> keys = routes.keySet();
				Iterator<String> it = keys.iterator();
				while(it.hasNext())
				{
					String key = it.next();
					if(key.split(Maps.ROUTE_JOINER).length == countTowns)
					{
						result.put(key, routes.get(key));
					}
				}
				//System.out.println(result);
				return result.size();
			}
		}
		
		return 0;
	}
	
	/**
	 * 获取两站之间的最短距离
	 * @param route
	 * @return
	 */
	public static int getShortestDistance(Route route)
	{
		Map<String, Route> routes = Maps.getRoutes(route, 100, false, true, 0);
		if (null != routes && !routes.isEmpty()) {
			//System.out.println(routes);
			List<Route> values = new ArrayList<Route>(routes.values());
			Route result = values.get(0);
			for(int i=1; i<values.size(); i++)
			{
				if(values.get(i).getDistance() < result.getDistance())
				{
					result = values.get(i);
				}
			}
			//System.out.println(result);
			return result.getDistance();
		}

		return 0;
	}
	
	/**
	 * 获取最大指定距离下的路线数
	 * @param route
	 * @param maxDistance
	 * @return
	 */
	public static int getMaxDistanceRoutesCount(Route route, int maxDistance)
	{
		Map<String, Route> result = Maps.getRoutes(route, 0, false, false, maxDistance);
		return null == result ? 0 : result.size();
	}
	
	/**
	 * 获取最大指定距离下的路线
	 * @param route
	 * @param maxDistance
	 * @return
	 */
	public static Map<String, Route> getMaxDistanceRoutes(Route route, int maxDistance)
	{
		return Maps.getRoutes(route, 0, false, false, maxDistance);
	}

	/**
	 * 查找路线
	 * @param route 带有起始站信息的路线
	 * @param countTowns 跨站数
	 * @param max 跨站数是否是最大站数 true：指定数最大跨站数 false：指定数为确定站数
	 * @param shortest 是否寻找最短路线 true：最短路线
	 * @param maxDistance 最大距离
	 * @return
	 */
	public static Map<String, Route> getRoutes(Route route, int countTowns,
			boolean max, boolean shortest, int maxDistance) {
		Town startTown = route.getStartTown();
		Town endTown = route.getEndTown();
		
		Map<String, Route> directTownsMap = Maps.directTownsMaps.get(startTown);
		
		Map<String, Route> result = new HashMap<String, Route>();
		
		StringBuffer sb = new StringBuffer(startTown.getName());
		sb.append("-");
		
		Set<String> keys = directTownsMap.keySet();
		Iterator<String> it = keys.iterator();
		
		// 无论是否最短路径都给定次数，防止起始站相互通，造成死循环
		while(it.hasNext() && (countTowns>0 || (countTowns > -1 && shortest) || maxDistance>0) )
		{
			String key = (String) it.next();
			if(key.equals(endTown.getName()))
			{
				sb.append(key);
				route.setRoutes(sb.toString());
				route.setDistance(Maps.directTownsMaps.get(startTown).get(key)
						.getDistance()
						+ route.getDistance());
				route.setCountTowns(2);
				result.put(sb.toString(), route);
				
				// 最短路径 出现则停止
				if(shortest)
				{
					break;
				}
				
				if(maxDistance > 0 && route.getDistance()>maxDistance)
				{
					result.remove(sb.toString());
					break;
				}
				
				// 不是指定最大站则允许继续往下找
				if(!max)
				{
					Route tmp = new Route(route.getStartTown(),
							route.getEndTown(), route.getRoutes(),
							route.getDistance()
									- Maps.directTownsMaps.get(startTown)
											.get(key).getDistance());

					innerIterator(tmp, countTowns, max, shortest, maxDistance,
							startTown, endTown, directTownsMap, result, sb, key);
				}
			}else
			{
				innerIterator(route, countTowns, max, shortest, maxDistance,
						startTown, endTown, directTownsMap, result, sb, key);
			}
		}
		
		return result;
	}

	
	private static void innerIterator(Route route, int countTowns, boolean max,
			boolean shortest, int maxDistance, Town startTown, Town endTown,
			Map<String, Route> directTownsMap, Map<String, Route> result,
			StringBuffer sb, String key) {
		
		Route tmp = new Route(directTownsMap.get(key).getEndTown(), endTown);
		tmp.setDistance(route.getDistance() + Maps.directTownsMaps.get(startTown).get(key).getDistance());
		Map<String, Route> tmpResult = Maps.getRoutes(tmp, countTowns - 1, max, shortest, maxDistance);
		Set<String> keystmp = tmpResult.keySet();
		Iterator<String> ittmp = keystmp.iterator();
		while(ittmp.hasNext())
		{
			String keytpm = ittmp.next();
			Route routetmp = new Route(startTown, tmpResult.get(keytpm).getEndTown());
			routetmp.setRoutes(startTown.getName() + "-" + keytpm);
			routetmp.setCountTowns(tmpResult.get(keytpm).getCountTowns() + 1);
			routetmp.setDistance(tmpResult.get(keytpm).getDistance());
			/*routetmp.setDistance(Maps.directTownsMaps.get(startTown)
					.get(key).getDistance()
					+ tmpResult.get(keytpm).getDistance());
*/
			result.put(routetmp.getRoutes(), routetmp);
			
			if(maxDistance > 0 && routetmp.getDistance()>maxDistance)
			{
				result.remove(sb.toString());
				continue;
			}
		}
	}

	private static void cacheRoute(Route route, Town startTown, int distance) {
		if (null != Maps.routeMaps) {
			route.setDistance(distance);
			if (Maps.routeMaps.containsKey(startTown)) {
				Maps.routeMaps.get(startTown).put(route.getRoutes(), route);
			} else {
				Map<String, Route> tmp = new HashMap<String, Route>();
				tmp.put(route.getRoutes(), route);
				Maps.routeMaps.put(startTown, tmp);
			}
		}
	}
	
}
