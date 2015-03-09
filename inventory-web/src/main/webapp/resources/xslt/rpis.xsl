<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">

    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>

    <xsl:template match="/raspberryPis">
        <html>
            <head>
                <title>Inventory</title>
                <link href="/inventory/resources/styles/basic.css" rel="stylesheet" type="text/css"/>
            </head>
            <body>
                <div id="panelwrap">
                    <div class="header">
                        <div class="title">
                            <h1>
                                <xsl:text>Inventory</xsl:text>
                            </h1>
                        </div>
                    </div>
                    <div class="center_content">
                        <div id="right_wrap">
                            <div id="center_content">
                                <h2>
                                    <xsl:text>There are </xsl:text>
                                    <xsl:value-of select="count(raspberryPi)"/>
                                    <xsl:text> items in the inventory.</xsl:text>
                                </h2>
                                <table class="rounded-corner">
                                    <thead>
                                        <tr>
                                            <th colspan="2">
                                                <xsl:text>Index</xsl:text>
                                            </th>
                                            <th>
                                                <xsl:text>Model</xsl:text>
                                            </th>
                                            <th>
                                                <xsl:text>Current purpose</xsl:text>
                                            </th>
                                            <th>
                                                <xsl:text>Note</xsl:text>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <xsl:apply-templates select="raspberryPi">
                                            <xsl:sort select="@id"/>
                                        </xsl:apply-templates>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td colspan="5">
                                                <xsl:text>* dynamic system info available (RaspiMon)</xsl:text>
                                            </td>
                                        </tr>
                                    </tfoot>
                                </table>
                                <object id="version-tag" data="version" type="text/plain"/>
                            </div>
                        </div>
                    </div>
                    <div class="clear"/>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="raspberryPi">
        <xsl:variable name="id" select="@id"/>

        <tr>

            <xsl:attribute name="class">
                <xsl:choose>
                    <xsl:when test="possession/commissioned != ''">
                        <xsl:value-of select="'back-lightred'" />
                    </xsl:when>
                    <xsl:when test="possession/purpose = ''">
                        <xsl:value-of select="'back-lightgreen'" />
                    </xsl:when>
                    <xsl:when test="possession/@owner != 'me'">
                        <xsl:value-of select="'back-lightgrey'" />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'back-lightblue'" />
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>

            <td>
                <b>
                    <xsl:text>rpi#</xsl:text>
                    <xsl:value-of select="@id"/>
                </b>
                <xsl:if test="system != ''">
                    <xsl:text>*</xsl:text>
                </xsl:if>
            </td>
            <td>
                <a href="/inventory/service/rpi/{$id}">
                    <xsl:text>details</xsl:text>
                </a>
            </td>
            <td>
                <xsl:value-of select="model/name"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="model/revision"/>
                <xsl:text> (</xsl:text>
                <xsl:value-of select="model/memory"/>
                <xsl:text>)</xsl:text>
            </td>
            <td>
                <xsl:value-of select="possession/purpose/@label"/>
            </td>
            <td class="note">
                <xsl:choose>
                    <xsl:when test="possession/commissioned != ''">
                        <xsl:text>commissioned</xsl:text>
                    </xsl:when>
                    <xsl:when test="possession/purpose = ''">
                        <xsl:text>free</xsl:text>
                    </xsl:when>
                    <xsl:when test="possession/@owner != 'me'">
                        <xsl:text>other</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'assigned'" />
                    </xsl:otherwise>
                </xsl:choose>
            </td>
        </tr>
    </xsl:template>


</xsl:stylesheet>