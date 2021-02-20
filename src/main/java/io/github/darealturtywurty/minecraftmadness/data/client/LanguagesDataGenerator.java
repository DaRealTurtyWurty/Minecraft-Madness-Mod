package io.github.darealturtywurty.minecraftmadness.data.client;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class LanguagesDataGenerator extends LanguageProvider {

	public LanguagesDataGenerator(DataGenerator gen, String modid, String locale) {
		super(gen, modid, locale);
	}

	@Override
	protected void addTranslations() {

	}

	public void addArmchairLang(RegistryObject<Block> block) {
		String[] blockNameWords = block.getId().getPath().split("_");
		String blockName = "";
		if (blockNameWords.length == 2) {
			blockName += blockNameWords[1] + blockNameWords[0] + blockNameWords[2];
		} else {
			blockName += String.join(" ", blockNameWords);
		}

		char[] charArray = blockName.toCharArray();
		boolean foundSpace = true;
		for (int i = 0; i < charArray.length; i++) {
			if (Character.isLetter(charArray[i])) {
				if (foundSpace) {
					charArray[i] = Character.toUpperCase(charArray[i]);
					foundSpace = false;
				}
			} else {
				foundSpace = true;
			}
		}
		blockName = String.valueOf(charArray);

		this.addBlock(block, blockName);
	}
}
