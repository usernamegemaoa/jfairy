/*
 * Copyright (c) 2013. Codearte
 */
package org.jfairy.producer.text;

import org.jfairy.data.DataMaster;
import org.jfairy.producer.BaseProducer;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.replaceChars;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.jfairy.producer.text.TextUtils.joinWithSpace;

class TextProducer {

	private static final String DATA = "text";
	private static final String ALPHABET = "alphabet";

	private final BaseProducer baseProducer;

	private final String loremIpsum;
	private final List<String> words;
	private final String alphabet;
	private final int maxAlphabetIndex;

	@Inject
	public TextProducer(DataMaster dataMaster, BaseProducer baseProducer) {
		this.baseProducer = baseProducer;
		loremIpsum = dataMaster.getString(DATA);
		words = asList(split(loremIpsum, ' '));
		alphabet = dataMaster.getString(ALPHABET);
		maxAlphabetIndex = alphabet.length() - 1;
	}

	public String getLoremIpsum() {
		return loremIpsum;
	}

	public String rawWords(int count, int precision) {
		List<String> result = readRawWords(count, precision);
		return joinWithSpace(result);
	}

	public String cleanWords(int count) {
		List<String> result = newArrayList();
		for (String part : readRawWords(count, 0)) {
			result.add(uncapitalize(replaceChars(part, "., ", "")));
		}
		return joinWithSpace(result);
	}

	public String randomString(int charsCount) {
		StringBuilder sb = new StringBuilder(charsCount);
		for (int i = 0; i < charsCount; i++) {
			sb.append(alphabet.charAt(baseProducer.randomInt(maxAlphabetIndex)));
		}
		return sb.toString();
	}

	private List<String> readRawWords(int count, int precision) {
		return baseProducer.randomElements(words, baseProducer.randomBetween(count, count + precision));
	}

}
