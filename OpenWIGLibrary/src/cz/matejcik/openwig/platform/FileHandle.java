/* 
 * Copyright (C) 2014 matejcik
 *
 * This program is covered by the GNU General Public License version 3 or any later version.
 * You can find the full license text at <http://www.gnu.org/licenses/gpl-3.0.html>.
 * No express or implied warranty of any kind is provided.
 */
package cz.matejcik.openwig.platform;

import java.io.*;

/** Platform-independent interface to file handles.
 * Provides ability to check whether a file exists, create or delete it
 * and open data streams for reading or writing.
 * Implementation must ensure that the underlying file is accessible for
 * both reading and writing.
 * <p>
 * FileHandle is used by Savegame to create save files only when needed
 * and to read and write game state data.
 */
public interface FileHandle {
	/** Opens a DataInputStream for reading */
	DataInputStream openDataInputStream () throws IOException;

	/** Opens a DataOutputStream for writing */
	DataOutputStream openDataOutputStream () throws IOException;

	/** Checks whether the underlying file exists */
	boolean exists () throws IOException;

	/** Creates the underlying file.
	 * This may fail if the file already exists - caller must ensure
	 * that the file is not present by calling exists().
	 */
	void create () throws IOException;

	/** Deletes the underlying file.
	 * This may fail if the file does not exist.
	 */
	void delete () throws IOException;

	/** Truncates the underlying file to a given length.
	 * This will reduce size of the file to a given length.
	 * @param len desired length of file.
	 */
	void truncate (long len) throws IOException;
}
