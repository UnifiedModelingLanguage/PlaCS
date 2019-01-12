<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0">
    <xsl:output method="text" encoding="UTF-8" indent="no"/>

    <xsl:param name="index_adj" select="document('res/dic/index_adj.xml')"/>
    <xsl:param name="index_adv" select="document('res/dic/index_adv.xml')"/>
    <xsl:param name="index_noun" select="document('res/dic/index_noun.xml')"/>
    <xsl:param name="index_verb" select="document('res/dic/index_verb.xml')"/>
    <xsl:template match="/sentence">

  	    <xsl:apply-templates/>
    </xsl:template>


    <xsl:template match="n">
        <xsl:variable name="query" select="."/>
        <xsl:value-of select="$index_noun/index_n/noun[position()=$query]/@name"/>|
    </xsl:template>
    <xsl:template match="a">
        <xsl:variable name="query" select="."/>
        <xsl:value-of select="$index_adj/index_a/adjective[position()=$query]/@name"/>|

    </xsl:template>
    <xsl:template match="v">
        <xsl:variable name="query" select="."/>
        <xsl:value-of select="$index_verb/index_v/verb[position()=$query]/@name"/>|

    </xsl:template>
    <xsl:template match="r">
        <xsl:variable name="query" select="."/>
        <xsl:value-of select="$index_adv/index_r/adverb[position()=$query]/@name"/>|

    </xsl:template>
    
    
    
</xsl:stylesheet>
