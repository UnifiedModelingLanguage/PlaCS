<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0">
    <xsl:output method="text" encoding="UTF-8" indent="no"/>
    
    <xsl:param name="data_adj" select="document('res/dic/data_adj.xml')"/>
    <xsl:param name="data_adv" select="document('res/dic/data_adv.xml')"/>
    <xsl:param name="data_noun" select="document('res/dic/data_noun.xml')"/>
    <xsl:param name="data_verb" select="document('res/dic/data_verb.xml')"/>
    <xsl:param name="exc_adj" select="document('res/dic/exc_adj.xml')"/>
    <xsl:param name="exc_adv" select="document('res/dic/exc_adv.xml')"/>
    <xsl:param name="exc_noun" select="document('res/dic/exc_noun.xml')"/>
    <xsl:param name="exc_verb" select="document('res/dic/exc_verb.xml')"/>
    <xsl:param name="index_adj" select="document('res/dic/index_adj.xml')"/>
    <xsl:param name="index_adv" select="document('res/dic/index_adv.xml')"/>
    <xsl:param name="index_noun" select="document('res/dic/index_noun.xml')"/>
    <xsl:param name="index_verb" select="document('res/dic/index_verb.xml')"/>
    <xsl:template match="/">
        <xsl:variable name="query" select="query"/>
        <xsl:variable name="noun_match" select="$index_noun/index_n/noun[@name=$query]"/>
        <xsl:variable name="verb_match" select="$index_verb/index_v/verb[@name=$query]"/>
        <xsl:variable name="adj_match" select="$index_adj/index_a/adjective[@name=$query]"/>
        <xsl:variable name="adv_match" select="$index_adv/index_r/adverb[@name=$query]"/>
            *adverb|
            <xsl:for-each select="$adv_match">
                <xsl:for-each select="rel">
                    <xsl:variable name="r" select="."/>
                    <xsl:for-each select="$data_adv/data_r/r[@rid=$r]">
                        <xsl:for-each select="m">
                            <xsl:value-of select="."/>|
                        </xsl:for-each>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:for-each>
        *verb|
            <xsl:for-each select="$verb_match">
                <xsl:for-each select="rel">
                    <xsl:variable name="v" select="."/>
                    <xsl:for-each select="$data_verb/data_v/v[@vid=$v]">
                        <xsl:for-each select="m">
                           <xsl:value-of select="."/>|
                        </xsl:for-each>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:for-each>
            *adjective|
            <xsl:for-each select="$adj_match">
                <xsl:for-each select="rel">
                    <xsl:variable name="a" select="."/>
                    <xsl:for-each select="$data_adj/data_a/a[@aid=$a]">
                        <xsl:for-each select="m">
                            <xsl:value-of select="."/>|
                        </xsl:for-each>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:for-each>
            *noun|
            <xsl:for-each select="$noun_match">
                <xsl:for-each select="rel">
                    <xsl:variable name="n" select="."/>
                    <xsl:for-each select="$data_noun/data_n/n[@nid=$n]">
                        <xsl:for-each select="m">
                            <xsl:value-of select="."/>|
                        </xsl:for-each>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:for-each>
    </xsl:template>
    
    
    
    
</xsl:stylesheet>
