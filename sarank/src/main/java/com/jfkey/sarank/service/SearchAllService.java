package com.jfkey.sarank.service;

import java.util.*;
import java.util.Map.Entry;

import com.jfkey.sarank.domain.*;
import com.jfkey.sarank.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.repository.SearchRepository;

/**
 * 
 * @author junfeng Liu
 * @time 5:53:16 PM Jul 3, 2018
 * @version v0.3.0
 * @desc
 */
@Service
public class SearchAllService {
	@Autowired
	private SearchRepository searchRepository;
	// author pie data.
	private Map<String, Object> authorPie;
	// conference pie data
	private Map<String, Object> confPie;
	private static final Logger LOG = LoggerFactory.getLogger(SearchAllService.class);

	public Map<String, Object> getAuthorPie () {
		LOG.info("authorPie: " + authorPie);
		return authorPie;
	}
	public Map<String, Object> getConfPie( ) {
		LOG.info("confPie: " + confPie);
		return confPie;
	}

	public Map<String, Object> search(SearchPara searchPara) {
		SearchType type = getSearchType(searchPara);
		if (type == SearchType.KEYWORDS) {
			return searchKeywords(searchPara);
		} else if (type == SearchType.AUTHOR) {
			return searchAuthor(searchPara);
		} else if (type == SearchType.AFFILIATION) {
			return searchAff(searchPara);
		} else if (type == SearchType.VENUE) {
			return searchVenue(searchPara);
		}
		return null; 
	}


	public ACJAShow getACJAShow (SearchPara searchPara) {
		String queryParam = searchPara.getFormatStr();
		// String rankType = getRtString(searchPara.getRt());
		String rankType = "3";
		int skip = 0;
		int limit  = Constants.PRE_PAGE_SIZE * 4;
		double alpha = Constants.RELEVANCE_LOW;
		double nor = Constants.C;
		
		List<String> acjaIDs = new ArrayList<String>();
		String wordList = "['data', 'mining']";
		String model = Constants.MODEL;
		Iterable<SearchHits> queryKeywords = searchRepository.queryByKeywords(queryParam, wordList , limit, skip, rankType, model, alpha);
		int allNumber = getHitsID(queryKeywords, acjaIDs);

		ACJAInfoHandler acjaInfoHandler = new ACJAInfoHandler(searchRepository.getACJAInfo(acjaIDs), allNumber, searchPara.getRt());
		ACJAShow acjaShow = acjaInfoHandler.getAcjaShow();
		authorPie = acjaInfoHandler.getSearchAuthorPie();
		confPie = acjaInfoHandler.getSearchConfPie();


		// getDetailACJAInfo(acjaShow, searchRepository.getACJAInfo(acjaIDs), allNumber);
		// acjaShow upper. 
		// acjaUpper(acjaShow);
		return acjaShow;
	}


	
	private Map<String, Object> searchKeywords(SearchPara searchPara) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String queryParam = searchPara.getFormatStr();
		String rankType = "3";
		int skip = Constants.PRE_PAGE_SIZE * (searchPara.getPage());
		int limit  = Constants.PRE_PAGE_SIZE * (searchPara.getPage() + 1);
		double alpha = Constants.RELEVANCE_LOW;
		double nor = Constants.C;
		String wordList = "['data', 'mining']";
		String model = Constants.MODEL;

		long t1 = System.currentTimeMillis();
		
		Iterable<SearchHits> queryKeywords = searchRepository.queryByKeywords(queryParam, wordList , limit, skip, rankType, model, alpha);

		Iterable<SearchHits> queryKeywords2 = searchRepository.queryByKeywords(queryParam, wordList , limit, skip, "fr", model, alpha);

		Iterable<SearchHits> queryKeywords3 = searchRepository.queryByKeywords(queryParam, wordList , limit, skip, "pr", model, alpha);

		long t2 = System.currentTimeMillis();
		LOG.info("search " + queryParam + ", spends " + (t2-t1) + " ms" );

		List<String> paIDs = new ArrayList<String>();
		List<String> paIDs2 = new ArrayList<String>();
		List<String> paIDs3 = new ArrayList<String>();

		int allNumber = getHitsID(queryKeywords, paIDs);
		allNumber = getHitsID(queryKeywords2, paIDs2);
		allNumber = getHitsID(queryKeywords3, paIDs3);

		t1 = System.currentTimeMillis();
		Iterable<PaperInSearchBean> searchedPaperIt = searchRepository.getPaperByIDs(paIDs);
		Iterable<PaperInSearchBean> searchedPaperIt2 = searchRepository.getPaperByIDsFR(paIDs2);
		Iterable<PaperInSearchBean> searchedPaperIt3 = searchRepository.getPaperByIDsPR(paIDs3);

		t2 = System.currentTimeMillis();
		LOG.info("search paper detailed info, spends " + (t2-t1) + " ms" );
		List<PaperInSearchBean> searchedPaperList = getIteratorData(searchedPaperIt);
		List<PaperInSearchBean> searchedPaperList2 = getIteratorData(searchedPaperIt2);
		List<PaperInSearchBean> searchedPaperList3 = getIteratorData(searchedPaperIt3);
		if (searchPara.getRt() == RankType.MOST_CITATION) {
			searchedPaperList.sort(new Comparator<PaperInSearchBean>() {
				@Override
				public int compare(PaperInSearchBean o1, PaperInSearchBean o2) {
					return o2.getCitations() - o1.getCitations();
				}
			});
		}
		// title to upper
		capitalize(searchedPaperList);
		capitalize(searchedPaperList2);

		// color searched keywords
		colorTitle(searchedPaperList, searchPara.getKeywords(), 1);
		colorTitle(searchedPaperList2, searchPara.getKeywords(), 1);

		PaperInSearchBean pib = searchedPaperList.get(0);
		pib.setVersions(31);
		searchedPaperList.set(0, pib);
		pib = searchedPaperList.get(1);
		pib.setVersions(12);
		searchedPaperList.set(1, pib);
		pib = searchedPaperList.get(2);
		pib.setVersions(12);
		searchedPaperList.set(2, pib);
		pib = searchedPaperList.get(3);
		pib.setVersions(4);
		searchedPaperList.set(3, pib);



		result.put("paperList", searchedPaperList);

		// List<PaperInSearchBean> searchedPaperList2 = new ArrayList<PaperInSearchBean>();
		// searchedPaperList2.addAll(searchedPaperList2);

//		Collections.sort(searchedPaperList2, new Comparator<PaperInSearchBean>() {
//	            @Override
//	            public int compare(PaperInSearchBean p1, PaperInSearchBean p2) {
//	            	if (p1.getPageRank() < p2.getPageRank()) {
//	            		return 1;
//	            	} else if (p1.getPageRank() > p2.getPageRank()) {
//	            		return -1;
//	            	} else {
//	            		return 0 ;
//	            	}
//	            }
//	        });

		result.put("paperList2", searchedPaperList2);
		result.put("paperList3", searchedPaperList3);
		
		
		// 3. get pagination info
		result.put("pager", new Pager(allNumber, searchPara.getPage(), Constants.BUTTONS_TO_SHOW));
		Map<String, Object> paper = new HashMap<String, Object>();
		paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
		paper.put("number", searchPara.getPage() );
		result.put("paper", paper);
		
		// Constants.SEARCH_TYPE) == SearchType.KEYWORDS
		result.put(Constants.SEARCH_TYPE, SearchType.KEYWORDS);
		
		return result;
	}
	

	private Map<String, Object> searchAuthor(SearchPara searchPara) {
		Map<String, Object> result = new HashMap<String, Object>();
		int skip = 0;
		int limit = 20;
		List<AuthorAffiliation> aaList = getIteratorData(searchRepository.searchAuthor(searchPara.getAuthor(), skip, limit));
		List<AuthorHit> listHits = new ArrayList<AuthorHit>();
		
		if (aaList.size() != 0) {
			// sort aff [ID, times]
			Map<String, Integer> affTimes = new HashMap<String, Integer>();
			String key = "";
			String affID = "";
			String affName = "";
			List<Map.Entry<String, Integer>> idTimeList = null;
			for (AuthorAffiliation aa: aaList) {
				affTimes.clear();
				for(int i = 0; i < aa.getAffID().length; i ++) {
					key = aa.getAffID()[i];
					if (affTimes.containsKey(key)) {
						affTimes.put(key, affTimes.get(key) + 1);
					} else {
						affTimes.put(key, 1);
					}
				}
				// sort ... 
				idTimeList = new ArrayList<Map.Entry<String, Integer>>(affTimes.entrySet());
				Collections.sort(idTimeList, new Comparator<Map.Entry<String, Integer>>() {
					@Override
					public int compare(Entry<String, Integer> o1,
							Entry<String, Integer> o2) {
						// TODO Auto-generated method stub
						return o2.getValue().compareTo(o1.getValue());
					}
				});
				if (idTimeList.size() == 0) {
					affID = "";
					affName = "";
				} else {
					affID = idTimeList.get(0).getKey();
					for (int i = 0; i < aa.getAffID().length; i ++) {
						if (affID.equals(aa.getAffID()[i])) {
							affName = aa.getAffName()[i];
							break;
						}
					}
				}
				listHits.add(new AuthorHit(aa.getAthID(), aa.getAthName(), affID, affName, 1, aa.getPaNumber()));
			}
		} 
	
		result.put("authors", listHits);
		result.put(Constants.SEARCH_TYPE, SearchType.AUTHOR);
		return result;
	}

	private Map<String, Object> searchAff(SearchPara searchPara) {
		int skip = 0;
		int limit = 20;
		
		String searchIntent = searchPara.getAffName().trim();
		String affReg = searchIntent + Constants.AFF_REG_SUFFIX;
		List<AffHit> searchedAffs = getIteratorData(searchRepository.getAffInfo(affReg, skip, limit));
		for (AffHit tmp : searchedAffs ){
			tmp.setAffName(FormatWords.sentenceToUpper(tmp.getAffName()));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("affs", searchedAffs);
		result.put("intent", searchIntent);
		// indicate search type
		result.put(Constants.SEARCH_TYPE, SearchType.AFFILIATION);
		return result;
	}

	private Map<String, Object> searchVenue(SearchPara searchPara) {
		Map<String, Object> res = new HashMap<>();
		String venName = searchPara.getVenName();
		List<VensHit> vensList = getIteratorData(searchRepository.searchVensByName1(venName));
		vensList.addAll(getIteratorData(searchRepository.searchVensByName2(venName)));

		res.put("vens", vensList);
		res.put(Constants.SEARCH_TYPE, SearchType.VENUE);

		return res;
	}
	
	private SearchType getSearchType(SearchPara para) {
		if (para.getVenName() != null && !para.getVenName().equals("")) {
			return SearchType.VENUE;
		} else if (para.getAffName() != null && !para.getAffName().equals("")) {
			return SearchType.AFFILIATION;
		} else if (para.getAuthor() != null && !para.getAuthor().equals("") ) {
			return SearchType.AUTHOR;
		} else if ( para.getKeywords() != null && !para.getKeywords().equals("")) {
			return SearchType.KEYWORDS;
		}else {
			return SearchType.KEYWORDS;
		}
	}
		// default ranking -- 1
		// relevance ranking -- 2
		// importance ranking -- 3
		// citation counts -- 4
		// publish time -- 5
		private String getRtString(RankType rt) {
			if (rt == RankType.DEFAULT_RANK) {	
				return "3";
			} else if (rt == RankType.RELEVANCE_RANK) {
				return "2";
			} else if (rt == RankType.IMPORTANCE_RANK) { 
				return "3";
			}else if (rt == RankType.MOST_CITATION) {
				return "4";
			} else if (rt == RankType.LATEST_YEAR) {
				return "5";
			} else {
				return "1";
			}
		}

	/**
	 * 
	 * @param it
	 *            Iterable Data
	 * @return get iterator data to List
	 */
	private <T> List<T> getIteratorData(Iterable<T> it) {
		List<T> list = new ArrayList<T>();
		if (it == null) {
			return null;
		} else {
			Iterator<T> iterator = it.iterator();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}
			return list;
		}
	}

	/**
	 * 
	 * @param paperScoresIt
	 *            Iterable of PaperScoresBean
	 * @param years
	 *            a Array of String.
	 * @return Traverse the Iterable, get list of PaperScoresBean and paper
	 *         published years.
	 */
	private List<PaperScoresBean> getIteratorDataAndYears(
			Iterable<PaperScoresBean> paperScoresIt, List<String> years) {
		int i = 0;
		List<PaperScoresBean> list = new ArrayList<PaperScoresBean>();
		Set<String> setYears = new TreeSet<String>();
		Iterator<PaperScoresBean> it = paperScoresIt.iterator();
		PaperScoresBean paScores;
		while (it.hasNext()) {
			paScores = it.next();
			setYears.add(paScores.getPaYear());
			list.add(paScores);
		}
		// get ACJAShow years.
		Iterator<String> yearsIt = setYears.iterator();
		while (yearsIt.hasNext()) {
			years.add(yearsIt.next());
		}
		return list;
	}

	private int getHitsID(Iterable<SearchHits> itHits, List<String> ids) {
		boolean first = true;
		int counts = 0;
		if (itHits == null) {
			return 0;
		} else {
			Iterator<SearchHits> it = itHits.iterator();
			while (it.hasNext()) {
				if (first) {
					counts = (int) it.next().getHitCounts();
					first = false;
				} else {
					ids.add(it.next().getNodeId());
				}
			}
		}
		return counts;
	}

	/**
	 * 
	 * @param ids
	 *            paper id.
	 * @param paperList
	 *            a list of {@link com.jfkey.sarank.domain.PaperInSearchBean}.
	 *            fix bug when query using aggregate function COLLECT in neo4j.
	 * 
	 */
	private void changeOrder(List<String> ids, List<PaperInSearchBean> paperList) {
		String tmpID = "";
		PaperInSearchBean pa = null;
		for (int i = 0; i < ids.size() - 1; i++) {
			tmpID = ids.get(i);
			for (int j = i; j < paperList.size(); j++) {
				if (tmpID.equals(paperList.get(j).getPaID())) {
					pa = paperList.get(i);
					paperList.set(i, paperList.get(j));
					paperList.set(j, pa);
					break;
				}
			}
		}
	}
	
//	private ACJAShow getDetailACJAInfo(ACJAShow acjaShow, Iterable<ACJA> ACJAIt, int paperSize) {
//	int ai = 0;
//		if (ACJAIt == null) {
//			return new ACJAShow();
//		} else {
//			Iterator<ACJA> it = ACJAIt.iterator();
//			ACJA acja = null;
//			Set<SortAff> setAff = new HashSet<SortAff>();
//			Set<SortAuthor> setAth = new HashSet<SortAuthor>();
//			Set<SortCon> setCon = new HashSet<SortCon>();
//			Set<SortJou> setJou = new HashSet<SortJou>();
//			Set<String> years = new HashSet<String>();
//			SortAff tmpA = null;
//			SortAuthor tmpAth = null;
//			SortCon tmpC = null;
//			SortJou tmpJ = null;
//			while (it.hasNext()) {
//				acja = it.next();
//				tmpA = null;
//				for (int i = 0; i < acja.getAffIDs().length; i ++) {
////					tmpA = new SortAff(acja.getAffIDs()[i], acja.getAffNames()[i], acja.getAffScores()[i]);
////					LOG.info("aff: " + tmpA.getAffID() + " , affName: " + tmpA.getAffName() + " score : " + tmpA.getScore());
////					if ( setAff.contains(tmpA) ){
////						Iterator<SortAff> ita = setAff.iterator();
////						SortAff itS = null;
////						while(ita.hasNext()) {
////							itS = ita.next();
////							if (itS.equals(tmpA)) {
////								itS.setScore(itS.getScore() + tmpA.getScore());
////							}
////						}
////					} else {
////						setAff.add(tmpA);
////					}
//					setAff.add(new SortAff(acja.getAffIDs()[i], acja.getAffNames()[i], acja.getAffScores()[i]));
//				}
//				ai ++;
//				for (int i = 0; i < acja.getAthIDs().length && ai < 5; i ++) {
//					tmpAth = new SortAuthor(acja.getAthIDs()[i],acja.getAths()[i], acja.getAthScores()[i]);
//					if (setAth.contains(tmpAth)) {
//						Iterator<SortAuthor> itAth = setAth.iterator();
//						SortAuthor itS = null;
//						while (itAth.hasNext()) {
//							itS = itAth.next();
//							if (itS.equals(tmpAth)) {
////								itS.setScore(itS.getScore() + tmpAth.getScore());
//							}
//						}
//
//					} else {
//						setAth.add(tmpAth);
//					}
//				}
//				if (acja.getConID() != null) {
//					tmpC = new SortCon(acja.getConID(), acja.getVenName(), acja.getVenScore(), acja.getPubYear()) ;
//					if (setCon.contains(tmpC)) {
//						Iterator<SortCon> itC = setCon.iterator();
//						SortCon itS = null;
//						while (itC.hasNext()){
//							itS = itC.next();
//							if (itS.equals(tmpC)) {
//								itS.setScore(itS.getScore() + tmpC.getScore());
//							}
//						}
//					} else {
//						setCon.add(tmpC);
//					}
//
//				}
//				if (acja.getJouID() != null) {
//					if (!acja.getVenName().equalsIgnoreCase("VLDB")){
//
//
//					tmpJ = new SortJou(acja.getJouID(), acja.getVenName(), acja.getVenScore(), acja.getPubYear());
//					if (setJou.contains(tmpJ)) {
//						Iterator<SortJou> itJ = setJou.iterator();
//						SortJou itS = null;
//						while (itJ.hasNext()) {
//							itS = itJ.next();
//							if (itS.equals(tmpJ)) {
////								itS.setScore(itS.getScore() +  tmpJ.getScore());
//
//							}
//						}
//
//					} else {
//						setJou.add(tmpJ);
//					}
//				}
//				}
//				years.add(acja.getPubYear());
//
//			}
////			years.add("1995");
//			years.add("2016");
////			Object[] affArr = setAff.stream().sorted().toArray();
////			Object[] athArr = setAth.stream().sorted().toArray();
////			Object[] conArr = setCon.stream().sorted().toArray();
////			Object[] jouArr = setJou.stream().sorted().toArray();
//
//			int size = (setAff.size() > Constants.ACJA_SHOW)  ? Constants.ACJA_SHOW : setAff.size();
//			setAff.stream().sorted().limit(size).forEach( item ->{
//				acjaShow.getAffID().add(item.getAffID());
//				acjaShow.getAffName().add(item.getAffName());
//				acjaShow.getAffScore().add(item.getScore());
//			});
//
//			setAth.stream().sorted().limit(size).forEach(item ->{
//				acjaShow.getAthID().add(item.getAthID());
//				acjaShow.getAthName().add(item.getAthName());
//				acjaShow.getAthScore().add(item.getScore());
//
//			});
//
//			setCon.stream().sorted().limit(size).forEach(item -> {
//				acjaShow.getConID().add(item.getConID());
//				acjaShow.getConName().add(item.getConName());
//				acjaShow.getConScore().add(item.getScore());
//			});
//
//			setJou.stream().sorted().limit(size).forEach(item -> {
//				acjaShow.getJouID().add(item.getJouID());
//				acjaShow.getJouName().add(item.getJouName());
//				acjaShow.getJouScore().add(item.getScore());
//			});
//
//			years.stream().sorted().forEach(item -> {
//				acjaShow.getYears().add(item);
//			});
//
//			// current years
//			//acjaShow.getYears().add("2016");
//
//			acjaShow.setAllPaperNum(paperSize);
//
//		}
//
//		List<String> jouName = acjaShow.getJouName();
//		acjaShow.getJouScore();
//		int dest = -1;
//		for (int i = 0; i < jouName.size(); i++) {
//			if (jouName.get(i).equalsIgnoreCase("SIGMOD")) {
//				jouName.remove(i);
//				dest = i;
//				break;
//			}
//		}
//		if (dest != -1) {
//			acjaShow.getJouID().remove(dest);
//			acjaShow.getJouScore().remove(dest);
//			dest = -1;
//		}
//
//		LOG.info("conference ID : " + acjaShow.getConID() + " , conference score : " + acjaShow.getConScore() + ", name : " + acjaShow.getConName() );
//		LOG.info("journal ID : " + acjaShow.getJouID() + " , journal score : " + acjaShow.getJouScore() + ", name : " + acjaShow.getJouName() );
//		LOG.info("author ID: " + acjaShow.getAthID() + ", author score: " + acjaShow.getAthScore() + ", name: " + acjaShow.getAthName());
//		LOG.info("affiliation: " + acjaShow.getAffID() + ", affName score : " + acjaShow.getAffScore() +", name : " + acjaShow.getAffName() );
//		return acjaShow;
//	}
//

	/**
	 * 
	 * @param list
	 * @param colored color the string
	 * @param type
	 */
	private void colorTitle(List<PaperInSearchBean> list, String colored,
			int type) {
		long start = System.currentTimeMillis();
		String colorType = "red";
		if (type == 1) {
			String[] split = colored.split(" ");
			String title = "";
			for (PaperInSearchBean tmp : list) {
				title = tmp.getTitle();
				for (String str : split) {
					str = FormatWords.upperWordFirstChar(str);
//					title = title.replaceAll("(?i)" + str, "<span class=\""
//							+ colorType + "\">" + str + "</span>");
					title = title.replaceAll("(?i)" + str, "<span style=\""
							+ "color:rgb(221,75,57)" + "\">" + str + "</span>");
				}
				tmp.setTitle(title);
				
			}
		} else if (type == 2) {
			// color Author.

		}
		long end = System.currentTimeMillis();
		System.out.println("color title spends : " + (end - start) + " ms");
	}
	
	private void acjaUpper(ACJAShow acja) {
		List<String> affName = acja.getAffName();
		for (int i = 0; i < affName.size(); i ++) {
			affName.set(i, FormatWords.sentenceToUpper(affName.get(i)));
		}
		List<String> conName = acja.getConName();
		for (int i = 0; i < conName.size(); i++) {
			conName.set(i, FormatWords.sentenceToUpper((conName.get(i))));
		}
		List<String> jouName = acja.getJouName();
		for (int i = 0; i < jouName.size(); i ++) {
			if (jouName.get(i).equalsIgnoreCase("sigmod")) {
				jouName.set(i, "SIGMOD RECORD");
			}
			jouName.set(i, FormatWords.upperAllChar((jouName.get(i))));
		}
		List<String> athName = acja.getAthName();
		for (int i = 0; i < athName.size(); i ++) {
			athName.set(i, FormatWords.sentenceToUpper((athName.get(i))));
		}
		
	}
	
	private void capitalize (List<PaperInSearchBean> list) {
		for (PaperInSearchBean tmp : list) {
			tmp.setTitle(FormatWords.sentenceToUpper((tmp.getTitle())));
//			String[] authors = tmp.getAuthors();
			int len = tmp.getAuthors().length > 5 ? 6 :tmp.getAuthors().length;
			String[] authors = new String[len];
			for (int i = 0; i < tmp.getAuthors().length; i ++) {
				authors[i] = FormatWords.sentenceToUpper((tmp.getAuthors()[i]));
//				if (i <= 4 ) {
//					authors[i] = sentenceToUpper(tmp.getAuthors()[i]);
//				} else if (i == 5) {
//					authors[i] = "...";
//				} else {
//					break;
//				}
			}
			
			tmp.setAuthors(authors);
		}
	}

//	/**
//	 *
//	 * @param tar
//	 * @return sentence level toUpper First Char
//	 */
//	private String sentenceToUpper(String tar) {
//		// a-z：97-122  	A-Z：65-90 0-9：48-57
//		StringBuilder sb = new StringBuilder();
//
//		String[] arr = tar.split(" ");
//		String[] lowerCase = {"and", "or", "of", "a", "in", "for"};
//		for (String tmpArr: arr) {
//			if (tmpArr.equals(lowerCase[0]) || tmpArr.equals(lowerCase[1]) || tmpArr.equals(lowerCase[2]) || tmpArr.equals(lowerCase[3]) ||tmpArr.equals(lowerCase[4]) ||tmpArr.equals(lowerCase[5]) ){
//			} else{
//				tmpArr = upperWordFirstChar(tmpArr);
//			}
//			sb.append(tmpArr + " ");
//		}
//		return sb.toString();
//	}
//
//	/**
//	 *
//	 * @param string
//	 * @return to upper word
//	 */
//	private String upperWordFirstChar(String string) {
//		char[] charArray = string.toCharArray();
//		if (charArray[0] >= 97 && charArray[0] <= 122 ) {
//			charArray[0] -= 32;
//		}
//		return String.valueOf(charArray);
//	}
//
//	private String upperAllChar(String string) {
//		char[] charArray = string.toCharArray();
//		for (int i = 0; i < charArray.length; i ++) {
//			if (charArray[i] >= 97 && charArray[i] <= 122 ) {
//				charArray[i] -= 32;
//			}
//		}
//		return String.valueOf(charArray);
//	}
//
}
