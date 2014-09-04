<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">

	<xsl:output method="xml" indent="yes" encoding="UTF-8" />

	<xsl:template match="/raspberryPis">
		<html>
			<head>
				<title>My Raspberry Pi Inventory</title>
				<link href="/inventory/resources/styles/basic.css" rel="stylesheet"
					type="text/css" />
			</head>
			<body>
				<div id="panelwrap">
					<div class="header">
						<div class="title">
							<h1>
								<xsl:text>My Raspberry Pi Inventory</xsl:text>
							</h1>
						</div>
					</div>
					<div class="center_content">
						<div id="right_wrap">
							<div id="center_content">
								<h2>
									<xsl:text>There are </xsl:text>
									<xsl:value-of select="count(raspberryPi)" />
									<xsl:text> raspberry pis in the inventory.</xsl:text>
								</h2>
								<table class="rounded-corner">
									<thead>
										<tr>
											<th>
												<xsl:text>Number</xsl:text>
											</th>
											<th>
												<xsl:text>Model</xsl:text>
											</th>
											<th>
												<xsl:text>Revision</xsl:text>
											</th>
											<th>
												<xsl:text>Memory</xsl:text>
											</th>
											<th>
												<xsl:text>Current purpose</xsl:text>
											</th>
										</tr>
									</thead>
									<tbody>
										<xsl:apply-templates select="raspberryPi">
											<xsl:sort select="@id" />
										</xsl:apply-templates>
									</tbody>
									<tfoot>
										<tr>
											<td colspan="5">
												<xsl:text>* commissioned, ** not owned by myself</xsl:text>
											</td>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="raspberryPi">
		<xsl:variable name="id" select="@id" />
		<tr>
			<td>
				<a href="/inventory/rpi/{$id}">
					<xsl:text>rpi#</xsl:text>
					<xsl:value-of select="@id" />
				</a>
				<xsl:if test="possession/@owner != 'me'">
					<xsl:text>**</xsl:text>
				</xsl:if>
				<xsl:if test="possession/commissioned != ''">
					<xsl:text>*</xsl:text>
				</xsl:if>
			</td>
			<td>
				<xsl:value-of select="model/name" />
			</td>
			<td>
				<xsl:value-of select="model/revision" />
			</td>
			<td>
				<xsl:value-of select="model/memory" />
			</td>
			<td>
				<xsl:value-of select="possession/purpose/@label" />
			</td>
		</tr>
	</xsl:template>


</xsl:stylesheet>