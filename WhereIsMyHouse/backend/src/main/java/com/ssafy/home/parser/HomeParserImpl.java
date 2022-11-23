package com.ssafy.home.parser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ssafy.home.model.dto.ParsingInfoDto;
import com.ssafy.home.model.mapper.HomeMapper;
import com.ssafy.util.URLMaker;

import lombok.AllArgsConstructor;
@Component
@AllArgsConstructor
public class HomeParserImpl implements HomeParser {

//	private static HomeParser homeParser;
//
//	public static HomeParser getHomeParser() {
//		return homeParser;
//	}

	public List<ParsingInfoDto> getOthersFromApi(String dongCode) throws SQLException {

		List<ParsingInfoDto> list = new ArrayList<>();
		String code = dongCode.substring(0, 5);

		final String[] homeType = { "Apt", "RH" };
		final String[] tradeType = { "Rent", "Trade" };

		for (int year = 2015; year <= 2022; year++) {
			for (int month = 1; month <= 12; month++) {
				for (int i = 0; i < homeType.length; i++) {
					list.addAll(parsingHomes(URLMaker.homeApiURL(tradeType[i], homeType[i], code, year, month),
							tradeType[i], homeType[i]));
				}
			}
		}

		for (int month = 1; month <= 9; month++) {
			for (int i = 0; i < homeType.length; i++) {
				list.addAll(parsingHomes(URLMaker.homeApiURL(tradeType[i], homeType[i], code, 2022, month),
						tradeType[i], homeType[i]));
			}
		}

		return list;
	}

	private List<ParsingInfoDto> parsingHomes(String url, String tradeType, String homeType) {

		List<ParsingInfoDto> list = new ArrayList<>();

		try {
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);

			// root tag
			doc.getDocumentElement().normalize();

			// 파싱할 tag
			NodeList nList = doc.getElementsByTagName("item");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					ParsingInfoDto parsingInfoDto = new ParsingInfoDto();

					if ("RH".equals(homeType))
						parsingInfoDto.setType("연립다세대");
					else if ("Apt".equals(homeType))
						parsingInfoDto.setType("아파트");

					parsingInfoDto.setApartmentName(getTagValue(parsingInfoDto.getType(), eElement));
					parsingInfoDto.setBuildYear(Integer.parseInt(getTagValue("건축년도", eElement)));
					parsingInfoDto.setDong(getTagValue("법정동", eElement));
					parsingInfoDto.setDongCode(getTagValue("지역코드", eElement));
					String jibun = getTagValue("지번", eElement);

					StringBuilder addr = new StringBuilder();

					addr.append(parsingInfoDto.getDong()).append(" ").append(jibun);
					
					parsingInfoDto.setAddress(addr.toString());

					if ("Trade".equals(tradeType)) {
						parsingInfoDto.setDealAmount(getTagValue("거래금액", eElement).trim());
						parsingInfoDto.setTradeType("매매");
					} else if ("Rent".equals(tradeType)) {
						String monthAmount = getTagValue("월세금액", eElement).trim();
						if (monthAmount.equals("0")) {
							parsingInfoDto.setDealAmount(getTagValue("보증금액", eElement).trim());
							parsingInfoDto.setTradeType("전세");
						} else {
							parsingInfoDto.setDealAmount(monthAmount);
							parsingInfoDto.setTradeType("월세");
						}
					}

					parsingInfoDto.setDealYear(Integer.parseInt(getTagValue("년", eElement)));
					parsingInfoDto.setDealMonth(Integer.parseInt(getTagValue("월", eElement)));
					parsingInfoDto.setDealDay(Integer.parseInt(getTagValue("일", eElement)));

					parsingInfoDto.setArea(getTagValue("전용면적", eElement));
					parsingInfoDto.setFloor(getTagValue("층", eElement));

					list.add(parsingInfoDto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}
}
