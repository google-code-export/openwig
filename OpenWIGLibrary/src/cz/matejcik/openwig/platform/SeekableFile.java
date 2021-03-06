/* 
 * Copyright (C) 2014 matejcik
 *
 * This program is covered by the GNU General Public License version 3 or any later version.
 * You can find the full license text at <http://www.gnu.org/licenses/gpl-3.0.html>.
 * No express or implied warranty of any kind is provided.
 */
package cz.matejcik.openwig.platform;

import java.io.IOException;

/** Simplified platform-independent interface to random-access data files. */
public interface SeekableFile {
	/** Moves internal file pointer so that next read will start
	 * at specified position.
	 * @param pos desired seek position
	 * @throws IOException
	 */
	public void seek (long pos) throws IOException;
	/** Returns current position in the file */
	public long position () throws IOException;
	/** Skips a specified number of bytes.
	 *
	 * This is logically equivalent to seek(position() + what).
	 * @param what length of skip
	 * @return number of skipped bytes
	 * @throws IOException
	 */
	public long skip (long what) throws IOException;

	/** Returns a two-byte integer number stored at current
	 * position in little-endian encoding.
	 */
	public short readShort () throws IOException;
	/** Returns a four-byte integer number stored at current
	 * position in little-endian encoding.
	 */
	public int readInt () throws IOException;
	/** Returns a double-precision floating-point number
	 * stored at current position in little-endian encoding.
	 */
	public double readDouble () throws IOException;
	/** Returns an eight-byte integer number stored at current
	 * position in little-endian encoding.
	 */
	public long readLong () throws IOException;
	/** Fills up the provided buffer.
	 * Reads up to buf.length bytes and stores them
	 * in the buf array. Blocks until buf is full, or until
	 * an end of file is reached
	 * @param buf the buffer to fill up
	 * @throws IOException
	 */
	public void readFully (byte[] buf) throws IOException;
	/** Reads and returns a null-terminated string.
	 * <p>
	 * No requirement on encoding, but UTF-8 would be nice.
	 */
	public String readString () throws IOException;

	/** Read and return a single byte from the file.
	 *
	 * @return a byte of data, or -1 when at end of file
	 */
	public int read () throws IOException;
}
