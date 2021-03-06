//
// Copyright 2011 EXANPE <exanpe@gmail.com>
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package fr.exanpe.t5.lib.internal.localesession;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.LocalizationSetter;
import org.apache.tapestry5.services.PageRenderRequestParameters;

import fr.exanpe.t5.lib.services.LocaleSessionService;

/**
 * Filter allowing to manage a custom locale in session, instead of in url.
 * 
 * @see LocaleSessionService to initialize your locale
 * @author jmaupoux
 * @since 1.2
 */
public class LocaleSessionRequestFilter implements ComponentRequestFilter
{
    private final LocalizationSetter localizationSetter;
    private final LocaleSessionService localeSessionService;

    /**
     * Constructor
     * 
     * @param localizationSetter the LocalizationSetter
     * @param service the locale session service
     */
    public LocaleSessionRequestFilter(LocalizationSetter localizationSetter, LocaleSessionService service)
    {
        this.localizationSetter = localizationSetter;
        this.localeSessionService = service;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apache.tapestry5.services.ComponentRequestFilter#handleComponentEvent(org.apache.tapestry5
     * .services.ComponentEventRequestParameters,
     * org.apache.tapestry5.services.ComponentRequestHandler)
     */
    public void handleComponentEvent(ComponentEventRequestParameters parameters, ComponentRequestHandler handler) throws IOException
    {
        restoreSession();
        handler.handleComponentEvent(parameters);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apache.tapestry5.services.ComponentRequestFilter#handlePageRender(org.apache.tapestry5
     * .services.PageRenderRequestParameters, org.apache.tapestry5.services.ComponentRequestHandler)
     */
    public void handlePageRender(PageRenderRequestParameters parameters, ComponentRequestHandler handler) throws IOException
    {
        restoreSession();
        handler.handlePageRender(parameters);
    }

    /**
     * Restore the locale from session
     */
    private void restoreSession()
    {
        String locale = localeSessionService.getLocale();

        if (StringUtils.isNotEmpty(locale))
        {
            localizationSetter.setNonPeristentLocaleFromLocaleName(locale);
        }
    }
}
