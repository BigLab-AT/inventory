<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">

	<xsl:output method="xml" indent="yes" encoding="UTF-8" />

	<xsl:template match="raspberryPi">
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
								<xsl:text>Raspberry Pi #</xsl:text>
								<xsl:value-of select="@id" />
								<xsl:if test="possession/@owner != 'me'">
									<xsl:text> (owned by </xsl:text>
									<xsl:value-of select="possession/@owner" />
									<xsl:text>)</xsl:text>
								</xsl:if>
							</h1>
						</div>
					</div>
					<div class="center_content">
						<div id="right_wrap">
							<div id="center_content">
								<xsl:apply-templates select="possession" />
								<xsl:if test="possession/purpose != ''">
									<table class="rounded-corner">
										<tbody>
											<xsl:apply-templates select="possession/purpose" />
										</tbody>
									</table>
								</xsl:if>
								<table class="rounded-corner">
									<thead>
										<tr>
											<th>
												<xsl:text>Hardware Specifications</xsl:text>
											</th>
										</tr>
									</thead>
									<tbody>
										<xsl:apply-templates select="model" />
										<xsl:if test="@serial">
											<tr>
												<td>
													<xsl:text>Serial Number </xsl:text>
													<b>
														<xsl:value-of select="@serial" />
													</b>
												</td>
											</tr>
										</xsl:if>
									</tbody>
								</table>
								<table class="rounded-corner">
									<xsl:if test="system">
										<thead>
											<tr>
												<th>
													<xsl:text>System Settings</xsl:text>
												</th>
											</tr>
										</thead>
										<tbody>
											<xsl:apply-templates select="system" />
										</tbody>
									</xsl:if>
									<tfoot>
										<tr>
											<td>
												<xsl:text>Purchased at </xsl:text>
												<xsl:value-of select="order/shop" />
												<xsl:text> (</xsl:text>
												<xsl:value-of select="substring(order/@date,0,11)" />
												<xsl:text>)</xsl:text>
												<xsl:if test="order/comment != ''">
													<xsl:text> **</xsl:text>
												</xsl:if>
											</td>
										</tr>
									</tfoot>
								</table>
								<xsl:if test="possession/comment != ''">
									<p class="comment">
										<xsl:text>* </xsl:text>
										<xsl:value-of select="possession/comment" />
									</p>
								</xsl:if>
								<xsl:if test="order/comment != ''">
									<p class="comment">
										<xsl:text>** </xsl:text>
										<xsl:value-of select="order/comment" />
									</p>
								</xsl:if>
								<div class="form_sub_buttons">
									<a class="button green" href="/inventory/rpi">
										<xsl:text>see all</xsl:text>
									</a>
								</div>
							</div>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="possession">
		<xsl:choose>
			<xsl:when test="purpose/@label">
				<h2>
					<xsl:value-of select="purpose/@label" />
					<xsl:if test="comment != ''">
						<xsl:text> *</xsl:text>
					</xsl:if>
				</h2>
			</xsl:when>
			<xsl:when test="commissioned != ''">
				<h2>
					<xsl:text>Commissioned! (to	</xsl:text>
					<xsl:value-of select="commissioned" />
					<xsl:text>)</xsl:text>
					<xsl:if test="comment != ''">
						<xsl:text> *</xsl:text>
					</xsl:if>
				</h2>
			</xsl:when>
			<xsl:otherwise>
				<h2>
					<xsl:text>no purpose yet</xsl:text>
					<xsl:if test="comment != ''">
						<xsl:text> *</xsl:text>
					</xsl:if>
				</h2>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="possession/purpose">
		<tr>
			<td>
				<span class="value">
					<xsl:value-of select="description" />
				</span>
			</td>
		</tr>
		<xsl:if test="references != ''">
			<tr>
				<td>
					<xsl:text>For further information visit</xsl:text>
					<ul>
						<xsl:for-each select="references/reference">
							<li>
								<xsl:variable name="source" select="@source" />
								<a href="{$source}">
									<xsl:value-of select="." />
								</a>
							</li>
						</xsl:for-each>
					</ul>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>

	<xsl:template match="model">
		<tr>
			<td>
				<b>
					<xsl:value-of select="name" />
				</b>
			</td>
		</tr>
		<tr>
			<td>
				<b>
					<xsl:value-of select="revision" />
				</b>
			</td>
		</tr>
		<tr>
			<td>
				<xsl:text>Memory </xsl:text>
				<b>
					<xsl:value-of select="memory" />
				</b>
			</td>
		</tr>
		<xsl:if test="@code">
			<tr>
				<td>
					<xsl:text>Hardware Revision Code </xsl:text>
					<b>
						<xsl:value-of select="@code" />
					</b>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>

	<xsl:template match="system">
		<tr>
			<td>
				<xsl:text>Last Updated </xsl:text>
				<b>
					<xsl:value-of select="substring(@lastUpdated, 0, 11)" />
					<xsl:text> </xsl:text>
					<xsl:value-of select="substring(@lastUpdated, 12, 8)" />
				</b>
			</td>
		</tr>
		<tr>
			<td>
				<xsl:text>Local Time </xsl:text>
				<b>
					<xsl:value-of select="substring(localTime, 0, 11)" />
					<xsl:text> </xsl:text>
					<xsl:value-of select="substring(localTime, 12, 8)" />

				</b>
			</td>
		</tr>
		<tr>
			<td>
				<xsl:text>Hostname </xsl:text>
				<span class="value">
					<xsl:value-of select="hostname" />
				</span>
			</td>
		</tr>
		<tr>
			<td>
				<xsl:text>OS </xsl:text>
				<b>
					<xsl:value-of select="os/name" />
				</b>
			</td>
		</tr>
		<tr>
			<td>
				<xsl:text>OS Version </xsl:text>
				<b>
					<xsl:value-of select="os/version" />
				</b>
			</td>
		</tr>
		<tr>
			<td>
				<xsl:text>Kernel Version </xsl:text>
				<b>
					<xsl:value-of select="os/kernel" />
				</b>
			</td>
		</tr>
		<xsl:if test="interfaces != ''">
			<tr>
				<td>
					<xsl:text>Interfaces </xsl:text>
					<ul>
						<xsl:for-each select="interfaces/inter">
							<li>
								<xsl:text>IPv</xsl:text>
								<xsl:value-of select="@version" />
								<xsl:text> </xsl:text>
								<b>
									<xsl:value-of select="address" />
								</b>
							</li>
						</xsl:for-each>
					</ul>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>